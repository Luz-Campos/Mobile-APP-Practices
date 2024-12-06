package com.example.um2;

public class InicioCursosDes {
    private String title;
    private String desc;
    private String img;
    private String dire;
    private String tel;

    public InicioCursosDes(String title, String desc, String img, String dire, String tel) {
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.dire = dire;
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDire() {
        return dire;
    }

    public void setDire(String dire) {
        this.dire = dire;
    }

    public String getTel() {return tel;}

    public void setTel(String tel) {this.tel = tel;}

    public InicioCursosDes(){
    }
}
