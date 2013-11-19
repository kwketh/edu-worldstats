package com.app.activities;

import java.util.Observable;
import java.util.Observer;

import com.app.MainApp;
import com.app.R;
import com.app.R.anim;
import com.app.R.id;
import com.app.R.layout;
import com.app.R.menu;
import com.app.adapters.CountryListAdapter;
import com.app.worldbankapi.Country;
import com.app.worldbankapi.CountryListResults;
import com.app.worldbankapi.WorldBankAPI;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ChooseCountry extends Activity implements Observer
{   
    /**
     * An adapter for the list referenced to countries array.
     */    
    CountryListAdapter countriesAdapter;
    
    /**
     * Country results.
     */
    CountryListResults results;     
    
    /**
     * The view listing all the countries. 
     */
    ListView listView;
    
    /**
     * Text field used for searching country by name. 
     */
    EditText textSearchCountry;
    
    /*
     * Loading progress dialog.
     */
    ProgressDialog progressDialog = null;
    
    void loadCountries() 
    {   
        MainApp app = (MainApp)this.getApplication();
       
        if (!app.hasData("countries")) 
        {
            results = WorldBankAPI.fetchCountryList();            
            app.storeData("countries", results);                 
        } 
        else
        {
            results = (CountryListResults)app.getData("countries"); 
        }
        
        results.addObserver(this);        

        if (!results.isLoaded())
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Waiting for country list...");
            progressDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);
        
        /* Get ListView object from the XML layouts */
        listView = (ListView)findViewById(R.id.country_list);                
        textSearchCountry = (EditText)findViewById(R.id.textSearchCountry);

        /* Show the Up button in the action bar. */
        setupActionBar();
        
        /* Retrieve list of all countries */
        loadCountries();     
        
        /**
         * ArrayAdapter constructor parameters:
         *   @param context - the context
         *   @param layout - the layout for the row
         *   @param id - an id of the TextView to which the data is written
         *   @param data - the array of data
         */    
        countriesAdapter = new CountryListAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, results.getCountries());

        /* Assign adapter to ListView */
        listView.setAdapter(countriesAdapter);
                
        /* ListView item click event */
        listView.setOnItemClickListener(new OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
                // ListView Clicked item value
                Country country = (Country)listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(), "You have selected country " + country.getName(), Toast.LENGTH_LONG).show();
                
                Intent intent = new Intent(getApplicationContext(), DisplayActivity.class);
                
                // todo: pass the country instance in the intent  
                intent.putExtra("countryCode", country.getCode());
                intent.putExtra("countryName", country.getName());
                intent.putExtra("capitalCity", country.getCapitalCity());
                
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);                
            }

        });

        textSearchCountry.addTextChangedListener(new TextWatcher() 
        {
            @Override
            public void afterTextChanged(Editable arg0) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String constraint = s.toString();
                
                /* Trim the white-spaces around the constraint */
                constraint = constraint.trim();
                
                /* Remove any duplicate white spaces */
                constraint = constraint.replaceAll("\\s+", " ");
                
                /* Apply the filter */
                countriesAdapter.getFilter().filter(constraint);
            }
        });

        textSearchCountry.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    textSearchCountry.clearFocus();
                    
                    /* Hide the keyboard */
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textSearchCountry.getWindowToken(), 0);
                    
                    return false;
                }
                return true;                
            }
         });
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() 
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_country, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * This method is called whenever the observed object has changed.
     * (in this case the CountryIndicatorResults object)
     * 
     * @param eventSource 
     *      the observable object triggering the event.
     * 
     * @param eventName
     *      an argument passed from the observable object.
     *      the value will always be an instance of String.
     * 
     * @see Observer#update
     */    
    @Override
    public void update(Observable eventSource, Object eventName) 
    {
        if (eventName.equals("fetchComplete")) 
        {
            countriesAdapter.notifyDataSetChanged();
            
            /* Hide the progress dialog */
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}
