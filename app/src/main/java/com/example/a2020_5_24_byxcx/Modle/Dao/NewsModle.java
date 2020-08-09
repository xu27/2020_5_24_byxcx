package com.example.a2020_5_24_byxcx.Modle.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class NewsModle {
    @Id
    private String id;
    @NotNull
    private String pid;
    private String url;
    private String img;
    private String title;
    private String source;

    @Generated(hash = 1247774746)
    public NewsModle(String id, @NotNull String pid, String url, String img,
                     String title, String source) {
        this.id = id;
        this.pid = pid;
        this.url = url;
        this.img = img;
        this.title = title;
        this.source = source;
    }

    @Generated(hash = 1589460761)
    public NewsModle() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
}
