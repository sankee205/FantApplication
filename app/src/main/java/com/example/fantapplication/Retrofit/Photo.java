package com.example.fantapplication.Retrofit;

public class Photo {
    String id;

    String name;

    long filesize;
    String mimeType;

    Item photoItem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Item getPhotoItem() {
        return photoItem;
    }

    public void setPhotoItem(Item photoItem) {
        this.photoItem = photoItem;
    }
}
