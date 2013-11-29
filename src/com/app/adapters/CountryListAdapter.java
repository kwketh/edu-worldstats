package com.app.adapters;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.MainApp;
import com.app.R;
import com.app.adapters.libs.FilterableAdapter;
import com.app.worldbankapi.Country;
import com.app.worldbankapi.CountryList;

/**
 * CountryListAdapter class.
 *
 * An adapter responsible for creating, displaying and
 * filtering countries.
 *
 * This extends FilterableAdapter which we have found made by
 * somebody (we have attached the MIT License and information
 * about the author in FilterableAdapter java file).
 *
 * FilterableAdapter helps to filter the adapter list in a
 * more customisable way which we have not been able to
 * achieve using a simple BaseAdapter or ArrayAdapter.
 */
public class CountryListAdapter extends FilterableAdapter<Country, String>
{
    Context m_context;

    public CountryListAdapter(Context context, int resource, int textViewResourceId, CountryList countries)
    {
        super(context, resource, textViewResourceId, countries);
        this.m_context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        TextView itemHeading = null;
        ImageView itemImage = null;

        if (view == null)
        {
            LayoutInflater mInflater = (LayoutInflater)m_context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.listitem_country, null);
        }

        Country country = getItem(position);

        itemHeading = (TextView)view.findViewById(R.id.itemCountryName);
        itemHeading.setText(country.getName());

        int flagImageResourceId = MainApp.getResourceById(country.getFlagResourceName(), m_context, R.drawable.class);
        itemImage = (ImageView)view.findViewById(R.id.itemCountryFlag);
        itemImage.setImageResource(flagImageResourceId);

        return view;
    }

    @Override
    public void notifyDataSetChanged()
    {
        /* Ensure no notifications are sent again */
        setNotifyOnChange(false);

        /* Then sort using default comparator. The default
         * comparator is obj1.compareTo(obj2) which class
         * Country implements. */
        this.sort(null);

        /* Finally notify the data has changed */
        super.notifyDataSetChanged();
    }

    @Override
    protected String prepareFilter(CharSequence seq)
    {
        return seq.toString().toLowerCase();
    }

    /**
     * Special filter for countries when searching.
     *
     * The method will be called for every country in the
     * list when the constraint changes.
     *
     * @param country the country being tested against the filter
     * @param constraint the text being searched for
     *
     * @return whether to show or hide the item in the list.
     *         true = show (the constraint matches the country)
     *         false = hide (the constraint does not match the country)
     */
    @Override
    protected boolean passesFilter(Country country, String constraint)
    {
        String countryName = country.getName().toLowerCase();

        /* Allow searching by exactly specifying the country name */
        if (countryName.startsWith(constraint))
            return true;

        /* Allow searching by country code */
        if (country.getCode().toLowerCase().equals(constraint))
            return true;

        /* Allow searching by capital city.
         * The constraint is required to be long enough to
         * not display too many unrelated countries */
        if (constraint.length() >= Math.min(3, country.getCapitalCity().length()) && country.getCapitalCity().toLowerCase().startsWith(constraint))
            return true;

        /* Allow searching using any constraint that matches any word in the country name */
        String[] words = constraint.split(" ");
        int[] indexes = new int[words.length];
        boolean didMatchConstrintWords = true;

        /* Iterate through every word in the constraint */
        for (int i = 0; i < words.length; i++)
        {
            int index;

            if (countryName.startsWith(words[i]))
                index = 0;
            else {
                index = countryName.indexOf(" " + words[i]);
                if (index == -1) {
                    didMatchConstrintWords = false;
                    break;
                }
            }

            /* Record an array of where every match appears in the country name */
            indexes[i] = index;
        }

        if (didMatchConstrintWords)
        {
            /* Ensure the matched indexes follow the country name in left-to-right order */
            int[] indexesSorted = indexes.clone();
            Arrays.sort(indexesSorted);
            if (Arrays.equals(indexes, indexesSorted))
                return true;
        }

        return false;
    }
}