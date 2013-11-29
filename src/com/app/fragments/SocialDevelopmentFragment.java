package com.app.fragments;

import com.app.R;
import com.app.worldbankapi.Indicator;

import android.widget.TextView;

public class SocialDevelopmentFragment extends GenericIndicatorsFragment
{
    private Indicator[] m_activeIndicators = {
        Indicator.LIFE_EXPECTANCY_MALE,
        Indicator.LIFE_EXPECTANCY_FEMALE,
        Indicator.PARLIAMENT_SEATS_FEMALE,
        Indicator.FERTILITY_RATE,
        Indicator.CPIA_GENDER_RATING,
    };

    TextView capitalCityText;

    @Override
    public Integer getFragmentId()
    {
        return R.layout.fragment_socialdevelopment;
    }

    @Override
    public Indicator[] getActiveIndicators()
    {
        return m_activeIndicators;
    }

}
