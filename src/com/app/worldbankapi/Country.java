package com.app.worldbankapi;

public class Country implements Comparable<Country>
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
        return getName();
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

    public String getFlagResourceName() {

        if (getCode().equals("MF"))
            return "france";

        if (getCode().equals("CG"))
            return "republic_of_the_congo";

        if (getCode().equals("CD"))
            return "congo";

        if (getCode().equals("KP"))
            return "north_korea";

        if (getCode().equals("KR"))
            return "south_korea";

        String name = getName();
        name = name.toLowerCase();
        name = name.replaceAll("\\s\\(.*", ""); // cut before brackets
        name = name.replaceAll(",.*", ""); // cut after ,
        name = name.replaceAll(" ", "_"); // change all spaces to underscores
        name = name.replaceAll("[^a-z,_]+", ""); // get rid of anything else, only leave underscores and alphabet

        return name;
    }

    @Override
    public int compareTo(Country country) 
    {
        Country country1 = this;
        Country country2 = country;
        
        String name1 = country1.getName();
        String name2 = country2.getName();
        
        return name1.compareToIgnoreCase(name2);
    }
}
