package model;

import java.io.Serializable;

/**
 * Created by Thanh-PC on 7/14/2017.
 */

public class CardVideo implements Serializable {
    String anh,tieude,mota,idvideo;

    public CardVideo() {
    }

    public CardVideo(String anh, String tieude, String mota, String idvideo) {
        this.anh = anh;
        this.tieude = tieude;
        this.mota = mota;
        this.idvideo = idvideo;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getIdvideo() {
        return idvideo;
    }

    public void setIdvideo(String idvideo) {
        this.idvideo = idvideo;
    }
}
