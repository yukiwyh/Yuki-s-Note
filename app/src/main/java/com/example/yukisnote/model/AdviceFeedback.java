package com.example.yukisnote.model;

import com.google.gson.annotations.Expose;

/**
 * Created by 钰辉 on 2015/8/29.
 */
public class AdviceFeedback {

    @Expose
    private String createdAt;
    @Expose
    private String objectId;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
