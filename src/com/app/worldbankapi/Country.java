package com.app.worldbankapi;

public class Country
{
    private String m_name;
    private String m_capitalCity;
    private String m_countryCode;
    
    private double m_longitude;
    private double m_latitude;
    
    /**
     * Constructor to represent a country.
     *  
     * @param countryCode
     *   the country code as string
     */
    public Country(String countryCode) {
        m_countryCode = countryCode;
    }    
    
    /**
     * Returns a representation of the object country code.
     */ 
    public String getCode() {
        return m_countryCode;
    }
        
    /**
     * Returns a representation of the object country code.
     */ 
    public String toString() {
        return "[code: " + getCode() + ", name: " + getName() + ", capital: " + getCapitalCity() + "]";
    }
    
    /* Setters */
    
    public void setName(String name) {
        m_name = name;
    }

    public void setCapitalCity(String capitalCity) {
        m_capitalCity = capitalCity;
    }

    public void setCoordinates(double longitude, double latitude) {
        m_longitude = longitude;
        m_latitude = latitude;
    }

    /* Getters */

    public String getName() {
        return m_name;
    }

    public String getCapitalCity() {
        return m_capitalCity;
    }

    public double getLatitude() {
        return m_latitude;
    }

    public double getLongitude() {
        return m_longitude;
    }
}
