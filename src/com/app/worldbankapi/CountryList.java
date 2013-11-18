package com.app.worldbankapi;

import java.util.ArrayList;

/** 
 * CountryList class.
 * 
 * Used to create an object representing a single country
 * or multiple countries.
 */
public class CountryList extends ArrayList<Country> 
{    
    /**
     * Constructor to initialize an empty list.
     */
    public CountryList() {
        super();
    }
    
    /**
     * Constructor to initialize with a single country.
     *  
     * @param countryCode
     *   the country code as string
     */
    public CountryList(String countryCode) {
        super();
        add(new Country(countryCode));
    }
    
    /**
     * Constructor to initialize with multiple countries. 
     *
     * @param countryCodes
     *   an array of country codes
     */
    public CountryList(String[] countryCodes) {
        super();
        for (String countryCode : countryCodes) {
            add(new Country(countryCode));
        }
    }

    /**
     * Returns a representation of the object country code(s)
     * used for the query.
     */ 
    public String getQueryData() {
        String countryCodes = "";
        if (size() > 0) {
            for (Country country : this) {
                countryCodes += country.getCode() + ";";
            }
            countryCodes = countryCodes.substring(0, countryCodes.length() - 1);
        }
        return countryCodes;
    }
}
