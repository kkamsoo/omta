package com.example.omta2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter<T> extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<T> data;
    String dataName;

    public ListViewAdapter(Context context, ArrayList<T> data, String dataName) {
        this.context = context;
        this.data = data;
        this.dataName = dataName;
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

        if(dataName.equals("NewsData")) {
            ArrayList<NewsData> newsData = (ArrayList<NewsData>) data;
            objectTitle.setText(newsData.get(position).getNewsTitl());
            objectSummary.setText(newsData.get(position).getCntntSumar());
        }

        else if(dataName.equals("SuccessData")) {
            ArrayList<SuccessData> successData = (ArrayList<SuccessData>) data;
            objectTitle.setText(successData.get(position).getTitl());
            objectSummary.setText(successData.get(position).getBdtCntnt());
        }

        else if(dataName.equals("NationData")) {
            ArrayList<NationData> nationData = (ArrayList<NationData>) data;
            objectTitle.setText(nationData.get(position).getNatnNm());
            objectSummary.setText(nationData.get(position).getPoltcCntnt());
        }

        else if(dataName.equals("ScamData")) {
            ArrayList<ScamData> scamData = (ArrayList<ScamData>) data;
            objectTitle.setText(scamData.get(position).getTitl());
            objectSummary.setText(scamData.get(position).getBdtCntnt());
        }

        else if(dataName.equals("ProductData")) {
            ArrayList<ProductData> productData = (ArrayList<ProductData>) data;
            objectTitle.setText(productData.get(position).getTitl());
            objectSummary.setText(productData.get(position).getBdtCntnt());
        }

        return view;
    }
}
