package com.app3c.application.blog;

public class BlogPost {
    public BlogPost() {
    }

    public BlogPost(String title,String content){
        this.title = title;
        this.content = content;
    }

    public BlogPost(String title, String content, String imageurl) {
        this.title = title;
        this.content = content;
        this.imageurl = imageurl;
    }

    public BlogPost(String title,String username,String content,String DateTime){

    }

    String title;
    String content;
    String date;
    String imageurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
