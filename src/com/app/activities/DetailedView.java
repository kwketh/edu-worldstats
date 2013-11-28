package com.app.activities;

import java.util.Observable;
import java.util.Observer;

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
    TextView name, definition;
    
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
           
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailed_view, menu);
        return true;
    }


    @Override
    public void update(Observable eventSource, Object eventName) 
    {
        IndicatorDefinitionResults results = (IndicatorDefinitionResults)eventSource;
        name.setText(results.getName());
        definition.setText(results.getDefinition());
    
    }
    
    
    
    
}
