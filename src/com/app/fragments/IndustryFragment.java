package com.app.fragments;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.app.R;
import com.app.worldbankapi.CountryIndicatorResults;
import com.app.worldbankapi.CountryList;
import com.app.worldbankapi.Indicator;
import com.app.worldbankapi.TimeseriesDataPoint;
import com.app.worldbankapi.WorldBankAPI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class IndustryFragment extends GenericIndicatorsFragment
{
    private Indicator[] m_activeIndicators = {
        Indicator.MALE_UNEMPLOYMENT,
        Indicator.FEMALE_UNEMPLOYMENT,
        Indicator.LABOUR_PARTICIPATION_MALE,
        Indicator.LABOUR_PARTICIPATION_FEMALE,
        Indicator.EMPLOYERS_MALE,
        Indicator.EMPLOYERS_FEMALE,
        Indicator.SELF_EMPLOYED_MALE,
        Indicator.SELF_EMPLOYED_FEMALE,
    };

    TextView capitalCityText;

    @Override
    public Integer getFragmentId()
    {
        return R.layout.industry_fragment;
    }

    @Override
    public Indicator[] getActiveIndicators()
    {
        return m_activeIndicators;
    }
}
