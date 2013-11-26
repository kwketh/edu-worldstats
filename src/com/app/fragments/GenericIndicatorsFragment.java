package com.app.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.Indicator;
import com.app.worldbankapi.TimeseriesDataPoint;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class GenericIndicatorsFragment extends Fragment implements Observer {

    /**
     * Returns a value label for a given indicator.
     *
     * This needs to be implemented by any subclass representing
     * values of indicators.
     *
     * @return
     */
    public abstract TextView getLabelForIndicator(Indicator indicator);

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
    public void update(Observable eventSource, Object eventName) {
        CountryIndicatorResults results = (CountryIndicatorResults) eventSource;
        TextView indicatorLabel = getLabelForIndicator(results.getIndicator());

        if (eventName.equals("fetchComplete")) {
            /* Retrieve the results object from the event source */
            ArrayList<TimeseriesDataPoint> points = results.getDataPoints();
            boolean hasData = points.size() > 0;
            indicatorLabel.setText(results.getIndicator().formatValue(hasData ? points.get(0) : null));
        } else
        if (eventName.equals("errorTimeout")) {
            indicatorLabel.setText("(timeout)");
        } else
        if (eventName.equals("errorJson")) {
            indicatorLabel.setText("(json error)");
        } else
        if (eventName.equals("errorNetwork")) {
            indicatorLabel.setText("(network error)");
        }

    }
}
