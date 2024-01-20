package com.matchasmarketplace.model;

public class AboutPhotos {

    private int aboutPhotosId;
    private String name;
    private String imgUrl;

    public AboutPhotos() {

    }

    public AboutPhotos(int aboutPhotosId, String name, String imgUrl) {
        this.aboutPhotosId = aboutPhotosId;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public int getAboutPhotosId() {
        return aboutPhotosId;
    }

    public void setAboutPhotosId(int aboutPhotosId) {
        this.aboutPhotosId = aboutPhotosId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
