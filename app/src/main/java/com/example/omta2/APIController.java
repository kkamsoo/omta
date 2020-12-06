package com.example.omta2;

import android.text.Html;
import android.text.SpannableString;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class APIController {
    public ArrayList<NewsData> getNewsFromAPI(String nation) throws IOException {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/ovseaMrktNewsService/ovseaMrktNews?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        ArrayList<NewsData> newsList = new ArrayList<>();

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                NewsData newsData = new NewsData("", ""); // 뉴스 데이터 하나 생성
                Elements newsTitle = element.select("newsTitl"); // 뉴스타이틀 데이터 가져오기
                newsData.newsTitl = newsTitle.text();

                Elements eles = element.select("cntntSumar"); // 뉴스요약 데이터 가져오기
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    newsData.cntntSumar += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(newsData.cntntSumar, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                newsData.cntntSumar = spanText.toString(); // 삭제하고 남은 스트링데이터
                newsList.add(newsData); // 최종 뉴스데이터를 뉴스 리스트에 추가
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
