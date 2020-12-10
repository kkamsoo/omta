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

public class GetNationAPI extends AsyncTask<Integer, Void, String> {
    ArrayList<NationData> nationList = new ArrayList<>();
    Context context;
    ProgressDialog progDialog;
    ListViewAdapter listAdapter;

    String nation = "";

    // Select메뉴 생성자
    public GetNationAPI(Context context, String nation) {
        this.context = context;
        this.nation = nation;
    }

    public GetNationAPI(Context context, ListViewAdapter listAdapter, String nation) {
        this.context = context;
        this.listAdapter = listAdapter;
        this.nation = nation;
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
        String queryUrl = "http://apis.data.go.kr/B410001/natnInfoService/natnInfo?ServiceKey=" + key + "&type=xml&isoWd2CntCd=" + nation;

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("resultMsg");
            // 데이터를 받지 못한경우
            if(elements.text().equals("NODATA_ERROR")) {
                NationData nationData = new NationData("", "", "");
                nationData.setNatnNm("API로부터 데이터를 받지 못하였습니다.");
                nationList.add(nationData);
            }
            else {
                elements = doc.select("item");

                for (Element element : elements) {
                    NationData nationData = new NationData("", "", "");

                    // 제목 데이터
                    Elements nationTitle = element.select("natnNm");
                    nationTitle = nationTitle.select("data");
                    nationData.natnNm = nationTitle.text();

                    // 요약 데이터
                    Elements nationSummary = element.select("clturCntnt");
                    nationSummary = nationSummary.select("data");
                    nationData.clturCntnt = nationSummary.text();

                    // 전체 데이터
                    Elements eles = element.select("data");
                    // 제목 다음부터 파싱
                    for (int i = 1; i < eles.size(); i++) {
                        nationData.content += eles.get(i).text() + "<br /><br />"; // 줄바꿈
                    }
                    nationData.content += "<br />< 저작권자 ⓒ KOTRA ＆ KOTRA 해외시장뉴스 ><br /><br />"; // 줄바꿈으로 스크롤 아래데이터 출력
                    SpannableString spanText = new SpannableString(Html.fromHtml(nationData.content, Html.FROM_HTML_MODE_COMPACT));
                    nationData.content = spanText.toString();
                    nationList.add(nationData);
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
            listAdapter.data = nationList;
            listAdapter.notifyDataSetChanged();
        }
        progDialog.cancel();
    }
}
