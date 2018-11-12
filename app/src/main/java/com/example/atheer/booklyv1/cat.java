package com.example.atheer.booklyv1;

public class cat {
    private String name;
    private  String imageUrl;
    private String key;
public cat(){

}

    public cat(String name,String imageUrl) {
        this.name = name;
        this.imageUrl=imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public cat(String name, String imageUrl, String key) {

        this.name = name;
        this.imageUrl = imageUrl;
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
