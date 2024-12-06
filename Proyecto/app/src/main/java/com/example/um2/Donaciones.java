package com.example.um2;

public class Donaciones {

    private String title;
    private String desc;
    private String img;
    private String marca;
    private String color;

    public Donaciones(String title, String desc, String img, String marca, String color) {
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.marca = marca;
        this.color = color;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {return color;}

    public void setColor(String color) {this.color = color;}

    public Donaciones(){
    }
}
