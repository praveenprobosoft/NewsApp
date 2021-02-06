package com.praveen.newsapp.network;

import com.praveen.newsapp.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("top-headlines?country=us&apiKey=f7700cfc631043b4ac7b06f5b65a19f1")
    Call<ResponseData> getTopHeadLines();

    @GET("everything?q=tesla&from=2021-01-06&sortBy=publishedAt&apiKey=f7700cfc631043b4ac7b06f5b65a19f1")
    Call<ResponseData> getLatestNews();
}
