package com.app.fragments;

import com.app.R;
import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.CountryList;
import com.app.worldbankapi.Indicator;
import com.app.worldbankapi.WorldBankAPI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.ArrayList;

public class BasicInfoFragment extends GenericIndicatorsFragment
{
    private Indicator[] m_activeIndicators = {
        Indicator.POPULATION,
        Indicator.GDP,
        Indicator.AREA_OF_COUNTRY,
        Indicator.GROWTH,
    };

    TextView capitalCityText;

    @Override
    public Integer getFragmentId()
    {
        return R.layout.basic_info_fragment;
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
        final String capitalCity = intent.getStringExtra("capitalCity");

        capitalCityText = (TextView)getRootView().findViewById(R.id.capitalCity);
        capitalCityText.setText(capitalCity);

        return getRootView();
    }
}
