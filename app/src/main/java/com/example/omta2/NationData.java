package com.example.omta2;

import java.io.Serializable;

public class NationData implements Serializable {
    String natnNm;
    String poltcCntnt;

    public NationData(String natnNm, String poltcCntnt) {
        this.natnNm = natnNm;
        this.poltcCntnt = poltcCntnt;
    }

    public String getNatnNm() {
        return natnNm;
    }

    public void setNatnNm(String natnNm) {
        this.natnNm = natnNm;
    }

    public String getPoltcCntnt() {
        return poltcCntnt;
    }

    public void setPoltcCntnt(String poltcCntnt) {
        this.poltcCntnt = poltcCntnt;
    }
}
