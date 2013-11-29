package com.app.activities;

import java.util.Observable;
import java.util.Observer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import com.app.R;
import com.app.worldbankapi.IndicatorDefinitionResults;
import com.app.worldbankapi.WorldBankAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;

public class IndicatorDetails extends Activity implements Observer, OnGestureListener
{
    private IndicatorDefinitionResults m_results;
    private TextView m_name;
    private TextView m_definition;
    private GestureDetector m_geastureDetector;

    ProgressDialog progressDialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_details);

        m_geastureDetector = new GestureDetector(this, this);

        m_name = (TextView) findViewById(R.id.indicatorName);
        m_definition = (TextView) findViewById(R.id.indicatorDefinition);

        Intent intent = getIntent();
        String indicator = intent.getStringExtra("indicator");
                
        m_results = WorldBankAPI.fetchIndicatorDefinition(indicator);
        m_results.addObserver(this);

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
        m_name.startAnimation(fadeOut);
        m_definition.startAnimation(fadeOut);
    }

    private void fadeInLabels()
    {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(350);
        m_name.startAnimation(fadeIn);
        m_definition.startAnimation(fadeIn);
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
        progressDialog.dismiss();

        if (eventName.equals("fetchComplete"))
        {
            m_name.setText(m_results.getName());
            m_definition.setText(m_results.getDefinition());
            fadeInLabels();
        } else
        {
            close();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.m_geastureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent)
    {
        if (m_results.areLoaded())
        {
            close();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {}

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }
}
