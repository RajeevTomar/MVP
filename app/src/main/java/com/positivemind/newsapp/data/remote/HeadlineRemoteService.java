package com.positivemind.newsapp.data.remote;

import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.server.BaseResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rajeev Tomar on 21,December,2019
 */
public interface HeadlineRemoteService {

    @GET("top-headlines")
    Single<BaseResponse<List<HeadlineModel>>> getTopHeadlines(@Query("country") String country);
}
