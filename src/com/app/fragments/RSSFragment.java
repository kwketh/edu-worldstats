package com.app.fragments;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.R;
import com.app.adapters.RSSAdapter;
import com.app.rssreader.RSSItem;
import com.app.rssreader.RSSService;

//RSS Reader made with help from a tutorial on androidresearch.wordpress.com 

public class RSSFragment extends Fragment implements OnItemClickListener
{

    private ProgressBar progressBar;
    private ListView listView;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.rss_fragment, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            startService();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), RSSService.class);
        intent.putExtra(RSSService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    /**
     * Once the {@link RssService} finishes its task, the result is sent to this
     * ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(
            new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);
            List<RSSItem> items = (List<RSSItem>) resultData
                    .getSerializable(RSSService.ITEMS);
            if (items != null) {
                RSSAdapter adapter = new RSSAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(),
                        "An error occured while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
        };
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        RSSAdapter adapter = (RSSAdapter)parent.getAdapter();
        RSSItem item = (RSSItem)adapter.getItem(position);
        if (item != null)
        {
            String link = item.getLink();
            if (!link.isEmpty())
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        }
    }
}