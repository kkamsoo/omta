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

public class GetSuccessAPI extends AsyncTask<Integer, Void, String> {
    ArrayList<SuccessData> successList = new ArrayList<>();
    Context context;
    ProgressDialog progDailog;
    ListViewAdapter listAdapter;

    String nation = "";
    String title = "";
    String date = "";

    // Select메뉴 생성자
    public GetSuccessAPI(Context context, String nation) {
        this.context = context;
        this.nation = nation;
    }

    public GetSuccessAPI(Context context, ListViewAdapter listAdapter, String nation, String title, String date) {
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
        String queryUrl = "http://apis.data.go.kr/B410001/compSucsCaseService/compSucsCase?ServiceKey="
                + key + "&type=xml&numOfRows=10&search1=" + title + "&search3=" + nation + "&search4=" + date;

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("resultMsg");
            // 데이터를 받지 못한경우
            if(elements.text().equals("NODATA_ERROR")) {
                SuccessData successData = new SuccessData("", "");
                successData.setTitl("API로부터 데이터를 받지 못하였습니다.");
                successList.add(successData);
            }
            else {
                elements = doc.select("item");
                for (Element element : elements) {
                    SuccessData successData = new SuccessData("", ""); // 데이터 하나 생성
                    Elements newsTitle = element.select("titl"); // 타이틀 데이터 가져오기
                    newsTitle = newsTitle.select("data");
                    successData.titl = newsTitle.text();

                    Elements eles = element.select("bdtCntnt");
                    for (Element ele : eles) {
                        Elements subNode = ele.select("data");
                        successData.bdtCntnt += subNode.text();
                    }
                    successData.bdtCntnt += "<br />< 저작권자 ⓒ KOTRA ＆ KOTRA 해외시장뉴스 ><br /><br />"; // 줄바꿈으로 스크롤 아래데이터 출력
                    SpannableString spanText = new SpannableString(Html.fromHtml(successData.bdtCntnt, Html.FROM_HTML_MODE_COMPACT));
                    successData.bdtCntnt = spanText.toString();
                    successList.add(successData);
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
            listAdapter.data = successList;
            listAdapter.notifyDataSetChanged();
        }
        progDailog.cancel();
    }
}
