package com.app.adapters;

import java.util.List;

import com.app.R;
import com.app.rssreader.RSSItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//RSS Reader made with help from a tutorial on androidresearch.wordpress.com 

public class RSSAdapter extends BaseAdapter {

        private final List<RSSItem> items;
        private final Context context;

        public RSSAdapter(Context context, List<RSSItem> items) {
                this.items = items;
                this.context = context;
        }

        @Override
        public int getCount() {
                return items.size();
        }

        @Override
        public Object getItem(int position) {
                return items.get(position);
        }

        @Override
        public long getItemId(int id) {
                return id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                        convertView = View.inflate(context, R.layout.listitem_rss, null);
                        holder = new ViewHolder();
                        holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
                        convertView.setTag(holder);
                } else {
                        holder = (ViewHolder) convertView.getTag();
                }
                holder.itemTitle.setText(items.get(position).getTitle());
                return convertView;
        }

        static class ViewHolder {
                TextView itemTitle;
        }
}