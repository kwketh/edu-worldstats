package com.app.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.worldbankapi.Country;
import com.app.worldbankapi.CountryList;

public class CountryListAdapter extends FilterableAdapter<Country, String> 
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

    /* this might be useful later to disable a row indicating a group */
    public boolean isEnabled(int position) {
        Country country = getItem(position);
        if (country != null && country.getCode() != "placeholder") {
            return true;
        }
        return false;
    }    

    @Override
    public void notifyDataSetChanged() 
    {
        /* Ensure no notifications are sent again */
        setNotifyOnChange(false);
        
        /* Then sort using default comparator. The default 
         * comparator is obj1.compareTo(obj2) which class
         * Country implements. */
        this.sort(null);
        
        /* Finally notify the data has changed */ 
        super.notifyDataSetChanged();
    }

    @Override
    protected String prepareFilter(CharSequence seq) 
    {
        return seq.toString().toLowerCase();
    }

    @Override
    protected boolean passesFilter(Country country, String constraint) 
    {
        String countryName = country.getName().toLowerCase();
        
        /* Allow searching by exactly specifying the country name */
        if (countryName.startsWith(constraint))
            return true;        

        /* Allow searching by country code */
        if (country.getCode().toLowerCase().equals(constraint))
            return true;
        
        /* Allow searching by capital city.
         * The constraint is required to be long enough to
         * not display too many unrelated countries */
        if (constraint.length() >= Math.min(5, country.getCapitalCity().length()) && country.getCapitalCity().toLowerCase().startsWith(constraint))
            return true;
        
        /* Allow searching using any constraint that matches any word in the country name */
        String[] words = constraint.split(" ");
        int[] indexes = new int[words.length];
        boolean didMatchConstrintWords = true;
        
        /* Iterate through every word in the constraint */
        for (int i = 0; i < words.length; i++)
        {
            int index;
            
            if (countryName.startsWith(words[i]))
                index = 0;
            else {
                index = countryName.indexOf(" " + words[i]);
                if (index == -1) {
                    didMatchConstrintWords = false;
                    break;
                }
            }
                        
            /* Record an array of where every match appears in the country name */
            indexes[i] = index;
        }
        
        if (didMatchConstrintWords) 
        {
            /* Ensure the matched indexes follow the country name in left-to-right order */
            int[] indexesSorted = indexes.clone();
            Arrays.sort(indexesSorted);
            if (Arrays.equals(indexes, indexesSorted))
                return true;
        }

        return false;
    }
}