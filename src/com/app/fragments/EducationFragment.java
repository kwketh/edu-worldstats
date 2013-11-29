package com.app.fragments;

import com.app.R;
import com.app.worldbankapi.Indicator;

import android.widget.TextView;

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
        return R.layout.fragment_education;
    }

    @Override
    public Indicator[] getActiveIndicators()
    {
        return m_activeIndicators;
    }
}