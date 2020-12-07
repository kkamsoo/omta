package com.example.omta2;

import java.io.Serializable;

public class SuccessData implements Serializable {
    public String titl;
    public String bdtCntnt;

    public SuccessData(String titl, String bdtCntnt) {
        this.titl = titl;
        this.bdtCntnt = bdtCntnt;
    }

    public String getBdtCntnt() {
        return bdtCntnt;
    }

    public void setBdtCntnt(String bdtCntnt) {
        this.bdtCntnt = bdtCntnt;
    }

    public String getTitl() {
        return titl;
    }

    public void setTitl(String titl) {
        this.titl = titl;
    }
}
