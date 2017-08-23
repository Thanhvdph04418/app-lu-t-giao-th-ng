package model;

import java.io.Serializable;

/**
 * Created by Thanh-PC on 7/15/2017.
 */

public class ItemCauhoi implements Serializable {
     private String socau,anh,dapan;

    public ItemCauhoi() {
    }

    public ItemCauhoi(String socau, String anh, String dapan) {
        this.socau = socau;
        this.anh = anh;
        this.dapan = dapan;
    }

    public String getSocau() {
        return socau;
    }

    public void setSocau(String socau) {
        this.socau = socau;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDapan() {
        return dapan;
    }

    public void setDapan(String dapan) {
        this.dapan = dapan;
    }
}
