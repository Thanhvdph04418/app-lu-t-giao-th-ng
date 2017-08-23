package model;

import java.io.Serializable;

/**
 * Created by Thanh-PC on 7/12/2017.
 */

public class coban implements Serializable {
    private String tieude;
    private String noidung;

    public coban() {
    }

    public coban(String tieude, String noidung) {
        this.tieude = tieude;
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    @Override
    public String toString() {
        return tieude;
    }
}
