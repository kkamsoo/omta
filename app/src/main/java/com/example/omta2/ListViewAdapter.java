package com.example.omta2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
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

    TextView objectTitle;
    TextView objectSummary;

    public ListViewAdapter(Context context, ArrayList<T> data) {
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

        objectTitle = view.findViewById(R.id.objectTitle);
        objectSummary = view.findViewById(R.id.objectSummary);

        if(data.get(0).getClass().getSimpleName().equals("NewsData")) {
            ArrayList<NewsData> newsData = (ArrayList<NewsData>) data;
            objectTitle.setText(newsData.get(position).getNewsTitl()); // 타이틀
            SpannableString spanText = new SpannableString(Html.fromHtml(newsData.get(position).getCntntSumar(), Html.FROM_HTML_MODE_COMPACT));
            objectSummary.setText(spanText.toString()); // 요약정보
        }

        else if(data.get(0).getClass().getSimpleName().equals("SuccessData")) {
            ArrayList<SuccessData> successData = (ArrayList<SuccessData>) data;
            objectTitle.setText(successData.get(position).getTitl());
            SpannableString spanText = new SpannableString(Html.fromHtml(successData.get(position).getBdtCntnt(), Html.FROM_HTML_MODE_COMPACT));
            objectSummary.setText(spanText.toString());
        }

        else if(data.get(0).getClass().getSimpleName().equals("NationData")) {
            ArrayList<NationData> nationData = (ArrayList<NationData>) data;
            objectTitle.setText(nationData.get(position).getNatnNm());
            SpannableString spanText = new SpannableString(Html.fromHtml(nationData.get(position).getClturCntnt(), Html.FROM_HTML_MODE_COMPACT));
            objectSummary.setText(spanText.toString());
        }

        else if(data.get(0).getClass().getSimpleName().equals("ScamData")) {
            ArrayList<ScamData> scamData = (ArrayList<ScamData>) data;
            objectTitle.setText(scamData.get(position).getTitl());
            SpannableString spanText = new SpannableString(Html.fromHtml(scamData.get(position).getBdtCntnt(), Html.FROM_HTML_MODE_COMPACT));
            objectSummary.setText(spanText.toString());
        }

        else if(data.get(0).getClass().getSimpleName().equals("ProductData")) {
            ArrayList<ProductData> productData = (ArrayList<ProductData>) data;
            objectTitle.setText(productData.get(position).getTitl());
            SpannableString spanText = new SpannableString(Html.fromHtml(productData.get(position).getBdtCntnt(), Html.FROM_HTML_MODE_COMPACT));
            objectSummary.setText(spanText.toString());
        }

        this.notifyDataSetChanged();

        return view;
    }
}
