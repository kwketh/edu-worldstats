package com.app.activities;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.app.R;
import com.app.worldbankapi.IndicatorDefinitionResults;
import com.app.worldbankapi.WorldBankAPI;

public class IndicatorDetails extends Activity implements Observer, OnGestureListener
{
    private IndicatorDefinitionResults m_results;
    private TextView name;
    private TextView definition;
    private GestureDetector geastureDetector;

    ProgressDialog progressDialog = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        geastureDetector = new GestureDetector(this, this);

        name = (TextView) findViewById(R.id.indicatorName);
        definition = (TextView) findViewById(R.id.indicatorDefinition);

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
        progressDialog.dismiss();

        if (eventName.equals("fetchComplete"))
        {
            name.setText(m_results.getName());
            definition.setText(m_results.getDefinition());
            fadeInLabels();
        } else
        {
            close();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.geastureDetector.onTouchEvent(event);
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
