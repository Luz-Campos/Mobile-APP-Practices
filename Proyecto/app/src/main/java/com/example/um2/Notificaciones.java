package com.example.um2;

public class Notificaciones {
    private String desc;
    private String img;

    public Notificaciones(String desc, String img) {
        this.desc = desc;
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {return img;}

    public void setImg(String img) {this.img = img;}

    public Notificaciones(){
    }
}
