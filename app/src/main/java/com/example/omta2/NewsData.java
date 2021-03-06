package com.example.omta2;

import android.text.Html;
import android.text.SpannableString;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;

public class NewsData implements Serializable {
    public String newsTitl; // 뉴스 제목
    public String newsBdt; // 뉴스 본문
    public String cntntSumar; // 뉴스 요약

    public NewsData(String newsTitl, String newsBdt, String cntntSumar){
        this.newsTitl = newsTitl;
        this.newsBdt = newsBdt;
        this.cntntSumar = cntntSumar;
    }

    public SpannableString getSpanSumar() { return spanSumar; }

    public void setSpanSumar(SpannableString spanSumar) { this.spanSumar = spanSumar; }

    public SpannableString spanSumar;

    public String getNewsTitl() {
        return newsTitl;
    }

    public void setNewsTitl(String newsTitl) {
        this.newsTitl = newsTitl;
    }

    public String getNewsBdt() { return newsBdt; }

    public void setNewsBdt(String newsBdt) { this.newsBdt = newsBdt; }

    public String getCntntSumar() {
        return cntntSumar;
    }

    public void setCntntSumar(String cntntSumar) {
        this.cntntSumar = cntntSumar;
    }
}
