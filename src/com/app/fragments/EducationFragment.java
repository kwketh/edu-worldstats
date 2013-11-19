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

public class EducationFragment extends Fragment
{

    SeekBar yearSeek;
    TextView year;
    TextView primaryEnrollment;
    TextView secondaryEnrollment;
    TextView tertiaryEnrollment;
    TextView maleLiteracyRate;
    TextView femaleLiteracyRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.education_fragment,
                container, false);

        year = (TextView) rootView.findViewById(R.id.year);
        primaryEnrollment = (TextView) rootView
                .findViewById(R.id.primaryEnrollment);
        secondaryEnrollment = (TextView) rootView
                .findViewById(R.id.secondaryEnrollment);
        tertiaryEnrollment = (TextView) rootView
                .findViewById(R.id.tertiaryEnrollment);
        maleLiteracyRate = (TextView) rootView
                .findViewById(R.id.maleLiteracyRate);
        femaleLiteracyRate = (TextView) rootView
                .findViewById(R.id.femaleLiteracyRate);
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