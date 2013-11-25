package com.app.worldbankapi;

public enum Indicator {

    /* Basic */

    // Total Population
    POPULATION("SP.POP.TOTL"),
    // GDP
    GDP("NY.GDP.MKTP.CD"),
    // Area Of Country
    AREA_OF_COUNTRY("AG.LND.TOTL.K2"),
    // Annual Growth
    GROWTH("NY.GDP.MKTP.KD.ZG"),

    /* Education */

    // Ratio of Female to Male (Primary Enrollment)
    RATIO_F_M_PRIMARY("SE.ENR.PRIM.FM.ZS"),
    // Ratio of Female to Male (Secondary Enrollment)
    RATIO_F_M_SECONDARY("SE.ENR.SECO.FM.ZS"),
    // Ratio of Female to Male (Tertiary Enrollment)
    RATIO_F_M_TERTIARY("SE.ENR.TERT.FM.ZS"),
    // Female Literacy Rate
    LITERACY_RATE_F("SE.ADT.LITR.FE.ZS"),
    // Male Literacy Rate
    LITERACY_RATE_M("SE.ADT.LITR.MA.ZS"),

    /* Industry */

    // Unemployment, female (% of female labor force)
    FEMALE_UNEMPLOYMENT("SL.UEM.TOTL.FE.ZS"),
    // Unemployment, male (% of male labor force)
    MALE_UNEMPLOYMENT("SL.UEM.TOTL.MA.ZS"),
    // Labor participation rate, female (% of female population ages 15+)
    LABOUR_PARTICIPATION_FEMALE("SL.TLF.CACT.FE.ZS"),
    // Labor participation rate, male (% of male population ages 15+)
    LABOUR_PARTICIPATION_MALE("SL.TLF.CACT.MA.ZS"),
    // Employers, female (% of employment)
    EMPLOYERS_FEMALE("SL.EMP.MPYR.FE.ZS"),
    // Employers, male (% of employment)
    EMPLOYERS_MALE("SL.EMP.MPYR.MA.ZS"),
    // Self-employed, female (% of females employed)
    SELF_EMPLOYED_FEMALE("SL.EMP.SELF.FE.ZS"),
    // Self-employed, male (% of males employed)
    SELF_EMPLOYED_MALE("SL.EMP.SELF.MA.ZS"),

    /* Social Development */

    // Life expectancy at birth, female (years)
    LIFE_EXPECTANCY_FEMALE("SP.DYN.LE00.FE.IN"),
    // Life expectancy at birth, male (years)
    LIFE_EXPECTANCY_MALE("SP.DYN.LE00.MA.IN"),
    // Proportion of seats held by women in national parliaments
    PARLIAMENT_SEATS_FEMALE("SG.GEN.PARL.ZS"),
    // CPIA gender equality rating (1=low, 6=high)
    CPIA_GENDER_RATING("IQ.CPA.GNDR.XQ"),
    // Fertility rate, total (births per woman)
    FERTILITY_RATE("SP.DYN.TFRT.IN");

    /* ... */
    /* add more here if we need them */

    private String m_id;

    Indicator(String indicatorId) {
        m_id = indicatorId;
    }

    public String getId() {
        return m_id;
    }
}
