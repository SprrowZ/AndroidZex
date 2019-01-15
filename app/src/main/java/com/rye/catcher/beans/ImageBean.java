package com.rye.catcher.beans;

/**
 * Created at 2019/1/15.
 *
 * @author Zzg
 * @function:
 */
public class ImageBean {
    private String url;
    private String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
