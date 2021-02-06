package com.praveen.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<ArticleModel> articleModelList;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleModel> getArticleModelList() {
        return articleModelList;
    }

    public void setArticleModelList(List<ArticleModel> articleModelList) {
        this.articleModelList = articleModelList;
    }
}


