package com.example.omta2;

import android.os.AsyncTask;
import android.text.Html;
import android.text.SpannableString;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetSuccessAPI extends AsyncTask<Integer, Void, Void> {
    ArrayList<SuccessData> successList = new ArrayList<>();
    String nation;

    public GetSuccessAPI(String nation) {
        this.nation = nation;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/compSucsCaseService/compSucsCase?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                SuccessData successData = new SuccessData("", ""); // 데이터 하나 생성
                Elements newsTitle = element.select("titl"); // 타이틀 데이터 가져오기
                successData.titl = newsTitle.text();

                Elements eles = element.select("bdtCntnt");
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    successData.bdtCntnt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(successData.bdtCntnt, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                successData.bdtCntnt = spanText.toString(); // 삭제하고 남은 스트링데이터
                successList.add(successData); // 최종 데이터
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
