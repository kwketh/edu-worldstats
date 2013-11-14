package com.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.app.examples.CountryPopulation;

public class MainActivity extends Activity 
{    
    Button btnChooseCountry;
    Button btnCompareCountries;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnChooseCountry = (Button) findViewById(R.id.btn_choose_a_country);
        btnCompareCountries = (Button) findViewById(R.id.btn_compare_countries);        

        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        startActivity(intent);
         
       // new CountryPopulation().run();
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
