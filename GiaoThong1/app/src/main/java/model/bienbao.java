package model;

import java.io.Serializable;

/**
 * Created by Thanh-PC on 7/12/2017.
 */

public class bienbao implements Serializable {
     public  String anh,noidung;

    public bienbao() {
    }

    public bienbao(String anh, String noidung) {
        this.anh = anh;
        this.noidung = noidung;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    @Override
    public String toString() {
        return "bienbao{" +
                "anh='" + anh + '\'' +
                ", noidung='" + noidung + '\'' +
                '}';
    }
}
