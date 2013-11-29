package com.app.fragments;

import android.widget.TextView;

import com.app.R;
import com.app.worldbankapi.Indicator;

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
