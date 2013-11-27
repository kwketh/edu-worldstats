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

public class EducationFragment extends GenericIndicatorsFragment
{
    private Indicator[] m_activeIndicators = {
        Indicator.RATIO_F_M_PRIMARY,
        Indicator.RATIO_F_M_SECONDARY,
        Indicator.RATIO_F_M_TERTIARY,
        Indicator.LITERACY_RATE_M,
        Indicator.LITERACY_RATE_F,
    };

    TextView capitalCityText;

    @Override
    public Integer getFragmentId()
    {
        return R.layout.education_fragment;
    }

    @Override
    public Indicator[] getActiveIndicators()
    {
        return m_activeIndicators;
    }
}