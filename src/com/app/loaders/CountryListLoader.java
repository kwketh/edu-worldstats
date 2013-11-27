package com.app.loaders;

import android.app.ProgressDialog;
import android.content.Context;
import com.app.MainApp;
import com.app.adapters.CountryListAdapter;
import com.app.worldbankapi.CountryListResults;
import com.app.worldbankapi.WorldBankAPI;

import java.util.Observable;
import java.util.Observer;

public class CountryListLoader implements Observer
{
    final private Context m_context;

    private CountryListAdapter m_adapter;
    private CountryListResults m_results;

    /*
     * Loading progress dialog.
     */
    private ProgressDialog m_progressDialog = null;

    /**
     * CountryListLoader constructor.
     *
     * @param context the context in which within the progress
     *                dialog will appear.
     */
    public CountryListLoader(Context context)
    {
        m_context = context;
    }

    public void load()
    {
        MainApp app = (MainApp)m_context.getApplicationContext();

        if (!app.hasData("countries"))
        {
            m_results = WorldBankAPI.fetchCountryList();
            m_adapter = new CountryListAdapter(m_context, android.R.layout.simple_list_item_1, android.R.id.text1, m_results.getCountries());
            app.storeData("countries", m_results);
        }
        else
        {
            m_results = (CountryListResults)app.getData("countries");
        }

        m_results.addObserver(this);

        if (!m_results.areLoaded())
        {
            m_progressDialog = new ProgressDialog(m_context);
            m_progressDialog.setCancelable(false);
            m_progressDialog.setTitle("Loading");
            m_progressDialog.setMessage("Waiting for country list");
            m_progressDialog.show();
        }
    }


    public CountryListAdapter getAdapter()
    {
        return m_adapter;
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
            m_adapter.notifyDataSetChanged();

            /* Hide the progress dialog */
            if (m_progressDialog != null)
            {
                m_progressDialog.dismiss();
                m_progressDialog = null;
            }
        }
    }
}
