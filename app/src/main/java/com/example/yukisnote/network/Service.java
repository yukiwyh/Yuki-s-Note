package com.example.yukisnote.network;

import com.example.yukisnote.model.Advice;
import com.example.yukisnote.model.AdviceFeedback;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by 钰辉 on 2015/8/29.
 */
public interface Service {
    @POST("/Advice")
    void sendAdvice(@Body Advice advice, Callback<AdviceFeedback> feedbackCallback);
}
