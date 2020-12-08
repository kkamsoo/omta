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

import java.io.IOException;
import java.util.ArrayList;

public class GetNewsAPI extends AsyncTask<Integer, Void, String> {
    ArrayList<NewsData> newsList = new ArrayList<>();
    Context context;
    ProgressDialog progDailog;
    ListViewAdapter listAdapter;

    String nation = "";
    String title = "";
    String date = "";

    // Select메뉴 생성자
    public GetNewsAPI(Context context, String nation) {
        this.context = context;
        this.nation = nation;
    }

    // Main컨트롤러 생성자
    public GetNewsAPI(Context context, ListViewAdapter listAdapter, String nation, String title, String date) {
        this.context = context;
        this.listAdapter = listAdapter;
        this.nation = nation;
        this.title = title;
        this.date = date;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(context);
        progDailog.setMessage("API로부터 데이터를 받는 중입니다..");
        progDailog.setCancelable(false);
        progDailog.show();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/ovseaMrktNewsService/ovseaMrktNews?ServiceKey="
                + key + "&type=xml&numOfRows=5&search1=" + nation + "&search2=" + title + "&search4=" + date;

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                NewsData newsData = new NewsData("", "", ""); // 뉴스 데이터 하나 생성
                Elements newsTitle = element.select("newsTitl"); // 뉴스타이틀 데이터 가져오기
                newsTitle = newsTitle.select("data");
                newsData.newsTitl = newsTitle.text();

                Elements eles = element.select("newsBdt"); // 뉴스요약 데이터 가져오기
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    newsData.newsBdt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(newsData.newsBdt, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                newsData.newsBdt = spanText.toString(); // 삭제하고 남은 스트링데이터

                Elements eles2 = element.select("cntntSumar"); // 뉴스요약 데이터 가져오기
                for (Element ele : eles2) {
                    Elements subNode = ele.select("data");
                    newsData.cntntSumar += subNode.text();
                }
                spanText = new SpannableString(Html.fromHtml(newsData.cntntSumar, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                newsData.cntntSumar = spanText.toString(); // 삭제하고 남은 스트링데이터
                newsList.add(newsData); // 최종 뉴스데이터를 뉴스 리스트에 추가
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
            listAdapter.data = newsList;
            listAdapter.notifyDataSetChanged();
        }
        progDailog.cancel();
    }
}
