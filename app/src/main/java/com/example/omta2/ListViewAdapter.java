package com.example.omta2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ListViewAdapter<T> extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<T> data;

    public ListViewAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.listview_layout, null);

        TextView objectTitle = (TextView)view.findViewById(R.id.objectTitle);
        TextView objectSummary = (TextView)view.findViewById(R.id.objectSummary);

        if(data.get(0).getClass().getSimpleName().equals("NewsData")) {
            ArrayList<NewsData> newsData = (ArrayList<NewsData>) data;
            objectTitle.setText(newsData.get(position).getNewsTitl());
            objectSummary.setText(newsData.get(position).getCntntSumar());
        }

        else if(data.get(0).getClass().getSimpleName().equals("SuccessData")) {
            ArrayList<SuccessData> successData = (ArrayList<SuccessData>) data;
            objectTitle.setText(successData.get(position).getTitl());
            objectSummary.setText(successData.get(position).getBdtCntnt());
        }

        else if(data.get(0).getClass().getSimpleName().equals("NationData")) {
            ArrayList<NationData> nationData = (ArrayList<NationData>) data;
            objectTitle.setText(nationData.get(position).getNatnNm());
            objectSummary.setText(nationData.get(position).getPoltcCntnt());
        }

        else if(data.get(0).getClass().getSimpleName().equals("ScamData")) {
            ArrayList<ScamData> scamData = (ArrayList<ScamData>) data;
            objectTitle.setText(scamData.get(position).getTitl());
            objectSummary.setText(scamData.get(position).getBdtCntnt());
        }

        else if(data.get(0).getClass().getSimpleName().equals("ProductData")) {
            ArrayList<ProductData> productData = (ArrayList<ProductData>) data;
            objectTitle.setText(productData.get(position).getTitl());
            objectSummary.setText(productData.get(position).getBdtCntnt());
        }
        this.notifyDataSetChanged();

        return view;
    }
}
