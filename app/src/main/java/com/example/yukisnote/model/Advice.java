package com.example.yukisnote.model;

import com.google.gson.annotations.Expose;

/**
 * Created by 钰辉 on 2015/8/29.
 */
public class Advice {

    @Expose
    private String AdviceContent;
    @Expose
    private String ContactInformation;

    public String getAdviceContent() {
        return AdviceContent;
    }

    public void setAdviceContent(String adviceContent) {
        AdviceContent = adviceContent;
    }

    public String getContactInformation() {
        return ContactInformation;
    }

    public void setContactInformation(String contactInformation) {
        ContactInformation = contactInformation;
    }
}
