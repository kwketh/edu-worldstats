package com.app;

import com.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BasicInfoFragment extends Fragment
{

    SeekBar yearSeek;
    TextView population;
    TextView populationComposition;
    TextView gdp;
    TextView gniPerCapita;
    TextView growth;
    TextView year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.basic_info_fragment,
                container, false);

        year = (TextView) rootView.findViewById(R.id.year);
        population = (TextView) rootView.findViewById(R.id.population);
        populationComposition = (TextView) rootView
                .findViewById(R.id.populationComposition);
        gdp = (TextView) rootView.findViewById(R.id.GDP);
        gniPerCapita = (TextView) rootView.findViewById(R.id.GNIPerCapita);
        growth = (TextView) rootView.findViewById(R.id.growth);
        yearSeek = (SeekBar) rootView.findViewById(R.id.seekBar1);

        yearSeek.setMax((53));
        yearSeek.setProgress(53);
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

    public void updateValues(int value) {
        year.setText((value + 1960) + "");
        population.setText("");
        populationComposition.setText("");
        gdp.setText("");
        gniPerCapita.setText("");
        growth.setText("");
    }
}
