package com.app.fragments;

import android.widget.TextView;

import com.app.R;
import com.app.worldbankapi.Indicator;

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