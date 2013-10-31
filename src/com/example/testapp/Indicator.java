package com.example.testapp;

public enum Indicator 
{
    POPULATION("SP.POP.TOTL"),
    SECONDARY_EDUCATION_DURATION("SE.SEC.DURS");
    /* ... */
    /* add more here if we need them */
    
    private String m_id;
    
    Indicator(String indicatorId) 
    { 
        m_id = indicatorId;
    }
    
    public String getId() 
    {
        return m_id;
    }
}
