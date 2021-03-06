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
    Context context;
    ProgressDialog progDialog;
    ListViewAdapter listAdapter;

    String nation = "";
    String title = "";
    String date = "";

    // Select메뉴 생성자
    public GetProductAPI(Context context, String nation) {
        this.context = context;
        this.nation = nation;
    }

    // Main컨트롤러 생성자
    public GetProductAPI(Context context, ListViewAdapter listAdapter, String nation, String title, String date) {
        this.context = context;
        this.listAdapter = listAdapter;
        this.nation = nation;
        this.title = title;
        this.date = date;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDialog = new ProgressDialog(context);
        progDialog.setMessage("API로부터 데이터를 받는 중입니다..");
        progDialog.setCancelable(false);
        progDialog.show();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/cmmdtDbService/cmmdtDb?ServiceKey="
                + key + "&type=xml&numOfRows=3&search1=" + nation + "&search2=" + title + "&search4=" + date;

        try {
            Connection conn = Jsoup.connect(queryUrl);
            Document doc = conn.get();
            Elements elements = doc.select("resultMsg");
            // 데이터를 받지 못한경우
            if(elements.text().equals("NODATA_ERROR")) {
                ProductData productData = new ProductData("", "");
                productData.setTitl("API로부터 데이터를 받지 못하였습니다.");
                productList.add(productData);
            }
            else {
                elements = doc.select("item");

                for (Element element : elements) {
                    ProductData productData = new ProductData("", "");
                    Elements productTitle = element.select("titl");
                    productTitle = productTitle.select("data");
                    productData.titl = productTitle.text();

                    Elements eles = element.select("bdtCntnt");
                    for (Element ele : eles) {
                        Elements subNode = ele.select("data");
                        productData.bdtCntnt += subNode.text();
                    }
                    productData.bdtCntnt += "<br /><p style=\"text-align: center;\"> 저작권자 ⓒ KOTRA ＆ KOTRA 해외시장뉴스 <br /><br />"; // 줄바꿈으로 스크롤 아래데이터 출력
                    productList.add(productData);
                }
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
        progDialog.cancel();
    }
}
