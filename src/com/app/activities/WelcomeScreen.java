package com.app.activities;

import com.app.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends Activity
{    
    Button btnChooseCountry;
    Button btnCompareCountries;
    Button btnRssReader;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        getWindow().setFormat(PixelFormat.RGBA_8888); 
        
        btnChooseCountry = (Button) findViewById(R.id.btn_choose_a_country);
        btnCompareCountries = (Button) findViewById(R.id.btn_compare_countries); 
        // btnRssReader = (Button) findViewById(R.id.btn_rss_reader);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }    
    
    /** Called when the user clicks the Choose A Country button */
    public void showChooseACountry(View view) {
        Intent intent = new Intent(this, ChooseCountry.class);
        startActivity(intent);
    }
    
    /** Called when the user clicks the Compare Countries button */
    public void showCompareCountries(View view){
        Intent intent = new Intent(this, CompareCountries.class);
        startActivity(intent);
    }

}
