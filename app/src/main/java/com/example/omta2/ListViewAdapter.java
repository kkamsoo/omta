package com.example.omta2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<NewsData> sample;

    public ListViewAdapter(Context context, ArrayList<NewsData> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public NewsData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.my_list_view, null);

        TextView newsTitle = (TextView)view.findViewById(R.id.newsTitle);
        TextView newsSummary = (TextView)view.findViewById(R.id.newsSummary);

        newsTitle.setText(sample.get(position).getNewsTitl());
        newsSummary.setText(sample.get(position).getCntntSumar());

        return view;
    }
}
