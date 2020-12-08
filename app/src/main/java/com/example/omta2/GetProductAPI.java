package com.example.omta2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableString;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetProductAPI extends AsyncTask<Integer, Void, String> {
    ArrayList<ProductData> productList = new ArrayList<>();
    String nation;
    Context context;
    ProgressDialog progDailog;

    ListViewAdapter listAdapter;

    // Select메뉴 생성자
    public GetProductAPI(Context context, String nation) {
        this.context = context;
        this.nation = nation;
    }

    // Main컨트롤러 생성자
    public GetProductAPI(Context context, String nation, ListViewAdapter listAdapter) {
        this.context = context;
        this.nation = nation;
        this.listAdapter = listAdapter;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(context);
        progDailog.setMessage("Loading");
        progDailog.setCancelable(false);
        progDailog.show();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/cmmdtDbService/cmmdtDb?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        try {
            Connection conn = Jsoup.connect(queryUrl);
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                ProductData productData = new ProductData("", "");
                Elements productTitle = element.select("titl");
                productData.titl = productTitle.text();

                Elements eles = element.select("cmdltNmKorn");
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    productData.bdtCntnt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(productData.bdtCntnt, Html.FROM_HTML_MODE_COMPACT));
                productData.bdtCntnt = spanText.toString();
                productList.add(productData);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(String result) {
        // 리스트어댑터가 있을때만 리스트 실시간 업데이트
        if(listAdapter != null) {
            listAdapter.data = productList;
            listAdapter.notifyDataSetChanged();
        }
        progDailog.cancel();
    }
}