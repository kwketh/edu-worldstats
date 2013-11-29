package com.app.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.view.View;
import com.app.R;
import com.app.activities.CountryDetails;
import com.app.worldbankapi.*;

import java.util.*;

/**
 * GenericIndicatorsFragment class.
 *
 * The class provides additional functionality for a regular fragment.
 *
 * The functionality provides fetching country indicator results
 * (only the active indicators used by the fragment instance) and
 * updating the fragment labels with new values.
 *
 * This is an abstract class to and therefore any fragment requiring
 * the above functionality needs to extend this class.
 */
public abstract class GenericIndicatorsFragment extends Fragment implements Observer
{
    private Context m_context;
    private View m_rootView;
    private String m_fetchDate;

    private static final Map<Indicator, Integer> m_textViewMap;
    static
    {
        m_textViewMap = new HashMap<Indicator, Integer>();

        /* Basic info fragment */
        m_textViewMap.put(Indicator.POPULATION, R.id.population);
        m_textViewMap.put(Indicator.AREA_OF_COUNTRY, R.id.countryArea);
        m_textViewMap.put(Indicator.GROWTH, R.id.growth);
        m_textViewMap.put(Indicator.GDP, R.id.GDP);

        /* Education fragment */
        m_textViewMap.put(Indicator.RATIO_F_M_PRIMARY, R.id.primaryEnrollment);
        m_textViewMap.put(Indicator.RATIO_F_M_SECONDARY, R.id.secondaryEnrollment);
        m_textViewMap.put(Indicator.RATIO_F_M_TERTIARY, R.id.tertiaryEnrollment);
        m_textViewMap.put(Indicator.LITERACY_RATE_M, R.id.maleLiteracyRate);
        m_textViewMap.put(Indicator.LITERACY_RATE_F, R.id.femaleLiteracyRate);

        /* Industry fragment */
        m_textViewMap.put(Indicator.MALE_UNEMPLOYMENT, R.id.maleUnemployed);
        m_textViewMap.put(Indicator.FEMALE_UNEMPLOYMENT, R.id.femaleUnemployed);
        m_textViewMap.put(Indicator.LABOUR_PARTICIPATION_MALE, R.id.maleParticipation);
        m_textViewMap.put(Indicator.LABOUR_PARTICIPATION_FEMALE, R.id.femaleParticipation);
        m_textViewMap.put(Indicator.EMPLOYERS_MALE, R.id.maleEmployers);
        m_textViewMap.put(Indicator.EMPLOYERS_FEMALE, R.id.femaleEmployers);
        m_textViewMap.put(Indicator.SELF_EMPLOYED_MALE, R.id.maleSelfEmployed);
        m_textViewMap.put(Indicator.SELF_EMPLOYED_FEMALE, R.id.femaleSelfEmployed);

        /* Social development fragment */
        m_textViewMap.put(Indicator.LIFE_EXPECTANCY_MALE, R.id.maleLifeExpectancy);
        m_textViewMap.put(Indicator.LIFE_EXPECTANCY_FEMALE, R.id.femaleLifeExpectancy);
        m_textViewMap.put(Indicator.PARLIAMENT_SEATS_FEMALE, R.id.proportionOfSeats);
        m_textViewMap.put(Indicator.FERTILITY_RATE, R.id.fertilityRate);
        m_textViewMap.put(Indicator.CPIA_GENDER_RATING, R.id.cpiaRating);
    }

    private final CountryList m_fetchCountries;
    private final Map<Indicator, CountryIndicatorResults> m_indicatorResultsMap;

    GenericIndicatorsFragment()
    {
        super();
        m_context = null;
        m_indicatorResultsMap = new HashMap<Indicator, CountryIndicatorResults>();
        m_fetchCountries = new CountryList();
        setFetchYear(CountryDetails.YEAR_DEFAULT);
    }

    public int getFetchYear()
    {
        return Integer.parseInt(m_fetchDate);
    }

    public void setFetchYear(int year)
    {
        m_fetchDate = String.valueOf(year);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        /* Create the view */
        m_rootView = inflater.inflate(getFragmentId(), container, false);
        m_context = (Context)getActivity().getApplicationContext();

        /* Get information about the intent */
        final Intent intent = getActivity().getIntent();

        ArrayList<Country> countryList = intent.getParcelableArrayListExtra("countries");

        /* Set up list of countries to fetch information about */
        m_fetchCountries.clear();
        for (Country country: countryList)
            m_fetchCountries.add(country);

        /* Update phase: fetch all the data required for the fragment  */
        fetchData();

        return m_rootView;
    }

    public void animateIn()
    {
        if (m_context == null)
            return;

        Animation fadeAnimation = AnimationUtils.loadAnimation(m_context, android.R.anim.fade_in);
        Animation slideAnimation = AnimationUtils.loadAnimation(m_context, R.anim.slide_in_text);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(fadeAnimation);
        animationSet.addAnimation(slideAnimation);

        for (Indicator indicator : getActiveIndicators())
        {
            TextView indicatorTextView = getTextViewForIndicator(indicator);
            indicatorTextView.startAnimation(animationSet);
        }
    }

    public void fetchData()
    {
        /* Check for any simple errors that should not happen in deployment */
        if (m_fetchCountries.isEmpty()) {
            System.out.println("Warning: fetchData() was called but no countries are specified");
            return;
        }

        if (getActiveIndicators().length == 0)
            throw new Error("fetchData() was called but no active indicators are specified");

        /* Loop through active indicators of this fragment instance
         * and fetch the new results */
        for (Indicator indicator : getActiveIndicators())
        {
            /* Update the indicator text view state to loading */
            TextView indicatorTextView = getTextViewForIndicator(indicator);

            if (m_fetchCountries.size() < 2) {
                indicatorTextView.setText(Html.fromHtml("<small><font color='grey'><small>(loading)</small></font></small>&nbsp;"));
                indicatorTextView.setTextSize(24);
            } else {
                indicatorTextView.setText(Html.fromHtml("<font color='grey'>(loading)</font>&nbsp;"));
                indicatorTextView.setTextSize(18);
            }

            /* If the results for the indicator exist already */
            if (m_indicatorResultsMap.containsKey(indicator))
            {
                CountryIndicatorResults results = m_indicatorResultsMap.get(indicator);
                if (results.areLoaded())
                    this.handleCompleteIndicatorResults(indicator, results);
            } else
            {
                /* Create new results for the indicator */
                CountryIndicatorResults results = WorldBankAPI.fetchCountriesIndicatorResults(m_fetchCountries, indicator, CountryDetails.YEAR_RANGE);

                /* Store the results in our HashMap data structure */
                m_indicatorResultsMap.put(indicator, results);

                /* Listen for any changes within the results */
                results.addObserver(this);
            }
        }

    }

    /**
     * Returns a value label for a given indicator.
     *
     * This needs to be implemented by any subclass representing
     * values of indicators.
     */
    final public TextView getTextViewForIndicator(Indicator indicator)
    {
        View view = getRootView();

        if (!m_textViewMap.containsKey(indicator))
             throw new Error("No text view found for indicator " + indicator);

        Integer id = m_textViewMap.get(indicator);
        TextView textView = (TextView)view.findViewById(id);

        if (textView == null)
            throw new Error("No text view found for indicator " + indicator);

        return textView;
    }

    /**
     * Returns id of the fragment.
     */
    abstract public Integer getFragmentId();

    /**
     * Returns a root view of the fragment.
     */
    public View getRootView()
    {
        return m_rootView;
    }

    /**
     * Returns a list of active indicators for the fragment.
     */
    abstract public Indicator[] getActiveIndicators();

    /**
     * This method is called whenever the observed object has changed. (in this
     * case the CountryIndicatorResults object)
     *
     * @param eventSource
     *            the observable object triggering the event.
     *
     * @param eventName
     *            an argument passed from the observable object. the value will
     *            always be an instance of String.
     *
     * @see java.util.Observer#update
     */
    @Override
    final public void update(Observable eventSource, Object eventName)
    {
        /* Retrieve the results from event source (by casting the event source to the results type) */
        CountryIndicatorResults results = (CountryIndicatorResults)eventSource;

        /* Gather information about the results */
        Indicator indicator = results.getIndicator();

        /* Ensure the event is coming from the most recent results. Sometimes
         * if fetching two results at the same time, we only want to listen
         * for updates coming from the most recent results. */
        if (m_indicatorResultsMap.get(indicator) != results)
            return;

        /* Get the text view used to represent the indicator value */
        TextView indicatorTextView = getTextViewForIndicator(indicator);

        /* Finally decide what to do depending on the event */
        if (eventName.equals("fetchComplete"))
        {
            handleCompleteIndicatorResults(indicator, results);
        } else
        if (eventName.equals("errorTimeout"))
            indicatorTextView.setText("(timeout)");
        else if (eventName.equals("errorParse"))
            indicatorTextView.setText("(json error)");
        else if (eventName.equals("errorNetwork"))
            indicatorTextView.setText("(network error)");
    }

    String getCountryColorByIndex(int index)
    {
        if (m_fetchCountries.size() < 2)
            return "#000000";

        if (index == 0)
            return "#0000AA";

        else if (index == 1)
            return "#AA0000";

        else if (index == 2)
            return "#00FF00";

        return "";
    }



    public String formatValuesToHtml(String[] formattedValues)
    {
        String htmlContents = "";
        for (int i = 0; i < formattedValues.length; i++) {
            String value = formattedValues[i];
            if (value.isEmpty()) {
                if (m_fetchCountries.size() < 2) {
                    htmlContents += "<small><font color='grey'><small>(no data)</small></font></small>&nbsp;";
                } else {
                    htmlContents += "<font color='grey'>(no data)</font>&nbsp;";
                }
            } else {
                String color = getCountryColorByIndex(i);
                htmlContents += "<font color='" + color + "'> " + value + "</font>&nbsp;";
            }
        }
        return htmlContents;
    }

    private void handleCompleteIndicatorResults(Indicator indicator, CountryIndicatorResults results)
    {
        /* Get a text view responsible for displaying value of the indicator */
        TextView indicatorTextView = getTextViewForIndicator(indicator);
        String[] countryValues = new String[m_fetchCountries.size()];

        if (m_fetchCountries.size() < 2) {
            indicatorTextView.setTextSize(24);
        } else {
            indicatorTextView.setTextSize(18);
        }

        for (int countryIndex = 0; countryIndex < m_fetchCountries.size(); countryIndex++)
        {
            /* Get the data points from results */
            ArrayList<CountryDataPoint> points = results.getDataPoints();
            points = CountryIndicatorResults.filterByYear(points, getFetchYear());
            points = CountryIndicatorResults.filterByCountry(points, m_fetchCountries.get(countryIndex));

            CountryDataPoint point = points.get(0);

            if (point != null && !point.isNullValue()) {
                /* Create representation of the indicator value into a 'pretty' formatted string,
                 * this usually depends on the indicator type (to show the unit) and the value
                 * it is representing (to show the right number of digits after the decimal point) */
                countryValues[countryIndex] = results.getIndicator().formatValue(point);
            } else {
                countryValues[countryIndex] = "";
            }
        }

        String htmlContents = formatValuesToHtml(countryValues);
        indicatorTextView.setText(Html.fromHtml(htmlContents));
    }
}
