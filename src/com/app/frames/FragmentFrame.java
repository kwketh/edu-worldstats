package com.app.frames;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import com.app.fragments.BasicInfoFragment;

public class FragmentFrame extends FrameLayout
{
    final private Fragment m_fragment;

    public FragmentFrame(Context context)
    {
        super(context);
        m_fragment = new BasicInfoFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
    }
}
