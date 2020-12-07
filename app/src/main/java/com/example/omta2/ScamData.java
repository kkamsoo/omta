package com.example.omta2;

import java.io.Serializable;

public class ScamData implements Serializable {
    String titl;
    String bdtCntnt;

    public ScamData(String titl, String bdtCntnt) {
        this.titl = titl;
        this.bdtCntnt = bdtCntnt;
    }

    public String getTitl() {
        return titl;
    }

    public void setTitl(String titl) {
        this.titl = titl;
    }

    public String getBdtCntnt() {
        return bdtCntnt;
    }

    public void setBdtCntnt(String bdtCntnt) {
        this.bdtCntnt = bdtCntnt;
    }
}
