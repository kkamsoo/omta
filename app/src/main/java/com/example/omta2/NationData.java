package com.example.omta2;

import java.io.Serializable;

public class NationData implements Serializable {
    String natnNm;
    String clturCntnt; // 요약 데이터(문화정보)
    String content; // 전체 데이터

    public NationData(String natnNm, String clturCntnt, String content) {
        this.natnNm = natnNm;
        this.clturCntnt = clturCntnt;
        this.content = content;
    }

    public String getNatnNm() {
        return natnNm;
    }

    public void setNatnNm(String natnNm) {
        this.natnNm = natnNm;
    }

    public String getClturCntnt() { return clturCntnt; }

    public void setClturCntnt(String clturCntnt) { this.clturCntnt = clturCntnt; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}
