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
    String nation;
    Context context;
    ProgressDialog dialog;

    public GetNationAPI(Context context, String nation) {
        this.context = context;
        this.nation = nation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("로딩중입니다...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/natnInfoService/natnInfo?ServiceKey=" + key + "&type=xml&isoWd2CntCd=" + nation;

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                NationData nationData = new NationData("", "");
                Elements nationTitle = element.select("natnNm");
                nationData.natnNm = nationTitle.text();

                Elements eles = element.select("poltcCntnt");
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    nationData.poltcCntnt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(nationData.poltcCntnt, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                nationData.poltcCntnt = spanText.toString();
                nationList.add(nationData);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
    }
}
