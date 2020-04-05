package com.positivemind.newsapp.server;


public class BaseResponse<T> {

    private String status;
    private T articles;
    private int totalResults;


    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getArticles() {
        return articles;
    }

    public void setArticles(T articles) {
        this.articles = articles;
    }
}
