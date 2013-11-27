package com.app.activities;

import java.util.Observable;
import java.util.Observer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.app.R;
import com.app.worldbankapi.IndicatorDefinitionResults;
import com.app.worldbankapi.WorldBankAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class DetailedView extends Activity implements Observer
{
    private TextView name;
    private TextView definition;

    ProgressDialog progressDialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
                
        name = (TextView) findViewById(R.id.indicatorName);
        definition = (TextView) findViewById(R.id.indicatorDefinition);

        Intent intent = getIntent();
        String indicator = intent.getStringExtra("indicator");
                
        IndicatorDefinitionResults results = WorldBankAPI.fetchIndicatorDefinition(indicator);
        results.addObserver(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading indicator details");
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                close();
            }
        });
        progressDialog.show();

        fadeOutLabels();
    }

    private void fadeOutLabels()
    {
        AlphaAnimation fadeOut = new AlphaAnimation(0.0f, 0.0f);
        fadeOut.setDuration(0);
        name.startAnimation(fadeOut);
        definition.startAnimation(fadeOut);
    }

    private void fadeInLabels()
    {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(350);
        name.startAnimation(fadeIn);
        definition.startAnimation(fadeIn);
    }

    @Override
    public void onBackPressed()
    {
        close();
    }

    public void close()
    {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                close();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable eventSource, Object eventName) 
    {
        IndicatorDefinitionResults results = (IndicatorDefinitionResults)eventSource;

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        if (eventName.equals("fetchComplete"))
        {
            name.setText(results.getName());
            definition.setText(results.getDefinition());
            fadeInLabels();
        } else
        {
            close();
        }
    }
    
}
