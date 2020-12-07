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

public class GetScamAPI extends AsyncTask<Integer, Void, Void> {
    ArrayList<ScamData> scamList = new ArrayList<>();
    String nation;

    public GetScamAPI(String nation) {
        this.nation = nation;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/cmmrcFraudCaseService/cmmrcFraudCase?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        try {
            Connection conn = Jsoup.connect(queryUrl);
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                ScamData scamData = new ScamData("", "");
                Elements scamTitle = element.select("titl");
                scamData.titl = scamTitle.text();

                Elements eles = element.select("bdtCntnt");
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    scamData.bdtCntnt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(scamData.bdtCntnt, Html.FROM_HTML_MODE_COMPACT));
                scamData.bdtCntnt = spanText.toString();
                scamList.add(scamData);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
