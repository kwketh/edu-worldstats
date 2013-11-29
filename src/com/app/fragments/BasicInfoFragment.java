package com.app.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.R;
import com.app.worldbankapi.Country;
import com.app.worldbankapi.Indicator;

public class BasicInfoFragment extends GenericIndicatorsFragment
{
    private Indicator[] m_activeIndicators =
    {
        Indicator.POPULATION,
        Indicator.GDP,
        Indicator.AREA_OF_COUNTRY,
        Indicator.GROWTH,
    };

    TextView capitalCityText;

    @Override
    public Integer getFragmentId()
    {
        return R.layout.fragment_country_basicinfo;
    }

    @Override
    public Indicator[] getActiveIndicators()
    {
        return m_activeIndicators;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        final Intent intent = getActivity().getIntent();
        ArrayList<Country> countryList = intent.getParcelableArrayListExtra("countries");

        String[] capitalCities = new String[countryList.size()];

        for (int i = 0; i < countryList.size(); i++)
            capitalCities[i] = countryList.get(i).getCapitalCity();

        capitalCityText = (TextView)getRootView().findViewById(R.id.capitalCity);
        capitalCityText.setText(Html.fromHtml(formatValuesToHtml(capitalCities)));

        return getRootView();
    }
}
