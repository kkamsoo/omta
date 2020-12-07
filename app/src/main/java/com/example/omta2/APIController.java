package com.example.omta2;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
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
    public ArrayList<SuccessData> getSuccessFromAPI(String nation) throws IOException {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/compSucsCaseService/compSucsCase?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        ArrayList<SuccessData> successList = new ArrayList<>();

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

        return successList;
    }
    public ArrayList<NationData> getNationFromAPI(String nation) throws IOException {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/natnInfoService/natnInfo?ServiceKey=" + key + "&type=xml&isoWd2CntCd=" + nation;

        ArrayList<NationData> nationList = new ArrayList<>();

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

        return nationList;
    }
    public ArrayList<ScamData> getScamFromAPI(String nation) throws IOException {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/cmmrcFraudCaseService/cmmrcFraudCase?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        ArrayList<ScamData> scamList = new ArrayList<>();

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                ScamData scamData = new ScamData("", ""); // 뉴스 데이터 하나 생성
                Elements scamTitle = element.select("titl"); // 뉴스타이틀 데이터 가져오기
                scamData.titl = scamTitle.text();

                Elements eles = element.select("bdtCntnt"); // 뉴스요약 데이터 가져오기
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    scamData.bdtCntnt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(scamData.bdtCntnt, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                scamData.bdtCntnt = spanText.toString(); // 삭제하고 남은 스트링데이터
                scamList.add(scamData); // 최종 뉴스데이터를 뉴스 리스트에 추가
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return scamList;
    }
    public ArrayList<ProductData> getProductFromAPI(String nation) throws IOException {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/cmmdtDbService/cmmdtDb?ServiceKey=" + key + "&type=xml&numOfRows=5&search1=" + nation;

        ArrayList<ProductData> productList = new ArrayList<>();

        try {
            Connection conn = Jsoup.connect(queryUrl); // Jsoup을 사용해서 웹페이지를 가져온다
            Document doc = conn.get();
            Elements elements = doc.select("item");

            for (Element element : elements) {
                ProductData productData = new ProductData("", ""); // 뉴스 데이터 하나 생성
                Elements productTitle = element.select("titl"); // 뉴스타이틀 데이터 가져오기
                productData.titl = productTitle.text();

                Elements eles = element.select("bdtCntnt"); // 뉴스요약 데이터 가져오기
                for (Element ele : eles) {
                    Elements subNode = ele.select("data");
                    productData.bdtCntnt += subNode.text();
                }
                SpannableString spanText = new SpannableString(Html.fromHtml(productData.bdtCntnt, Html.FROM_HTML_MODE_COMPACT)); // 필요없는 태그 데이터를 삭제해준다.
                productData.bdtCntnt = spanText.toString(); // 삭제하고 남은 스트링데이터
                productList.add(productData); // 최종 뉴스데이터를 뉴스 리스트에 추가
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return productList;
    }
}
