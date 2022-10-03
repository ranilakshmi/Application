package com.app3c.application.feed;

public class Post {
    String heading, subheading, detail;

    public Post() {
    }
    public Post(String heading,String subheading,String detail){
        this.heading = heading;
        this.subheading = subheading;
        this.detail = detail;
    }
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
