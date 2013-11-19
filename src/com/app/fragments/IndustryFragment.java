package com.app.fragments;

import com.app.R;
import com.app.R.id;
import com.app.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class IndustryFragment extends Fragment
{
    SeekBar yearSeek;
    TextView year;
    TextView maleUnemployed;
    TextView femaleUnemployed;
    TextView maleLabourParticipation;
    TextView femaleLabourParticipation;
    TextView maleEmployers;
    TextView femaleEmployers;
    TextView maleSelfEmployed;
    TextView femaleSelfEmployed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.industry_fragment, container,
                false);

        maleUnemployed = (TextView) rootView.findViewById(R.id.maleUnemployed);
        femaleUnemployed = (TextView) rootView
                .findViewById(R.id.femaleUnemployed);
        maleLabourParticipation = (TextView) rootView
                .findViewById(R.id.maleParticipation);
        femaleLabourParticipation = (TextView) rootView
                .findViewById(R.id.femaleParticipation);
        maleEmployers = (TextView) rootView.findViewById(R.id.maleEmployers);
        femaleEmployers = (TextView) rootView.findViewById(R.id.femaleEmployers);
        maleSelfEmployed = (TextView) rootView.findViewById(R.id.maleSelfEmployed);
        femaleSelfEmployed = (TextView) rootView.findViewById(R.id.femaleSelfEmployed);
        
        year = (TextView) rootView.findViewById(R.id.year);
        yearSeek = (SeekBar) rootView.findViewById(R.id.seekBar);
        updateValues(yearSeek.getProgress());
        
        yearSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                updateValues(arg1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }
        });
        
        
        return rootView;
    }

    private void updateValues(int value) {
        year.setText((value + 1960) + "");
        // Update all fields here
    }

}
