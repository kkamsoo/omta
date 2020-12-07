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

public class GetProductAPI extends AsyncTask<Integer, Void, Void> {
    ArrayList<ProductData> productList = new ArrayList<>();
    String nation;

    public GetProductAPI(String nation) {
        this.nation = nation;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
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
}
