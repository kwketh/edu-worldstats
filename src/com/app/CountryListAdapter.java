package com.app;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.worldbankapi.Country;
import com.app.worldbankapi.CountryList;

public class CountryListAdapter extends ArrayAdapter<Country> 
{
    Context context;
    CountryList countries;
    
    public CountryListAdapter(Context context, int resource,
            int textViewResourceId, CountryList countries) {
        super(context, resource, textViewResourceId, countries);
        this.context = context;
        this.countries = countries;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;        
        TextView textHeading = null;
        
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(android.R.layout.simple_list_item_1, null);                       
        }
        
        Country country = getItem(position);
        
        textHeading = (TextView) view.findViewById(android.R.id.text1);
        textHeading.setText(country.getName());

        return view;
    }
    
    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        Country country = getItem(position);
        if (country != null && country.getCode() != "placeholder") {
            return true;
        }
        return false;
    }    
    
}