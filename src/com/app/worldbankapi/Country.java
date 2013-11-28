package com.app.worldbankapi;

import android.os.Parcel;
import android.os.Parcelable;

public class Country implements Comparable<Country>, Parcelable
{
    private String m_name;
    private String m_capitalCity;
    private String m_countryCode;
    
    private double m_longitude;
    private double m_latitude;

    /**
     * Constructor from country code (partial data).
     *  
     * @param countryCode
     *   the country code as string
     */
    public Country(String countryCode)
    {
        m_countryCode = countryCode;
    }

    /**
     * Constructor from Parcel object (used to pass
     * data between two intents)
     *
     * @param parcel
     *   the parcelable object
     */
    public Country(Parcel parcel)
    {
        readFromParcel(parcel);
    }

    /**
     * Returns a representation of the object country code.
     */ 
    public String getCode()
    {
        return m_countryCode;
    }
 
    /**
     * Returns a representation of the object country code.
     */ 
    public String toString()
    {
        return getName();
    }

    /* Setters */
    
    public void setName(String name)
    {
        m_name = name;
    }

    public void setCapitalCity(String capitalCity)
    {
        m_capitalCity = capitalCity;
    }

    public void setCoordinates(double longitude, double latitude)
    {
        m_longitude = longitude;
        m_latitude = latitude;
    }

    /* Getters */

    public String getName()
    {
        return m_name;
    }

    public String getCapitalCity()
    {
        return m_capitalCity;
    }

    public double getLatitude()
    {
        return m_latitude;
    }

    public double getLongitude()
    {
        return m_longitude;
    }

    public String getFlagResourceName()
    {
        /* Flag exceptions, just map them manually by country code
         * because of their tricky name */
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

        /* Otherwise, map them automatically - this works for the rest */

        String name = getName();
        name = name.toLowerCase(); // make lowercase
        name = name.replaceAll("\\s\\(.*", ""); // remove anything after brackets
        name = name.replaceAll(",.*", ""); // remove anything after comma
        name = name.replaceAll(" ", "_"); // replace all spaces to underscores
        name = name.replaceAll("[^a-z,_]+", ""); // only keep alphabetic characters, commas and underscores.
        return name;
    }

    /**
     * Used and implemented by Comparable to compare
     * two countries (normally in a list) and sort them
     * by the country name alphabetically.
     *
     * @param country
     *
     * @return
     *  -1 = move backwards
     *   0 = stay in the same position
     *   1 = move forward
     *
     *   @see Comparable
     */
    @Override
    public int compareTo(Country country) 
    {
        Country country1 = this;
        Country country2 = country;
        
        String name1 = country1.getName();
        String name2 = country2.getName();
        
        return name1.compareToIgnoreCase(name2);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(m_name);
        parcel.writeString(m_capitalCity);
        parcel.writeString(m_countryCode);
        parcel.writeDouble(m_longitude);
        parcel.writeDouble(m_latitude);
    }

    public void readFromParcel(Parcel parcel)
    {
        m_name = parcel.readString();
        m_capitalCity = parcel.readString();
        m_countryCode = parcel.readString();
        m_longitude = parcel.readDouble();
        m_latitude = parcel.readDouble();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>()
    {
        public Country createFromParcel(Parcel in)
        {
            return new Country(in);
        }

        public Country[] newArray(int size)
        {
            return new Country[size];
        }
    };

}
