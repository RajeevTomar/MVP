package com.positivemind.newsapp.db.headline;

import com.positivemind.newsapp.headline.HeadlineModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rajeev Tomar on 16,December,2019
 */
public interface HeadlineDataSource {
    // getAll
    Observable<List<HeadlineModel>> getAll();

    // insert
    void insertHeadlineModel(HeadlineModel headlineModel);

    // delete
    void deleteHeadlineModel(HeadlineModel headlineModel);

    void insertAll(List<HeadlineModel> headlineModelList);

    //void deleteAll();
    void deleteAll();
}
