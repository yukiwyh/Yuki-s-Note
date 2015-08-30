package com.example.yukisnote.network;

import com.example.yukisnote.common.Constant;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by 钰辉 on 2015/8/29.
 */
public class RestClient {

    private static RestAdapter restClient;
    public static Service getInstance()
    {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-AVOSCloud-Application-Id","IgIVjd5TgsuhvxVxyk1ODaq1");
                request.addHeader("X-AVOSCloud-Application-Key","4yncvJ4XMnC2W2Bjdrg5ca5Q");
                request.addHeader("Content-Type","application/json");
            }
        };
        if(restClient == null)
        {
            restClient = new RestAdapter.Builder()
                            .setEndpoint(Constant.HOME).
                            setRequestInterceptor(requestInterceptor).build();
        }
        Service service = restClient.create(Service.class);
        return service;
    }
}
