package com.positivemind.newsapp.data.local;

import com.positivemind.newsapp.db.headline.HeadlineDao;
import com.positivemind.newsapp.db.headline.HeadlineDataSource;
import com.positivemind.newsapp.headline.HeadlineModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rajeev Tomar on 21,December,2019
 */
public class HeadlineLocalDataSource implements HeadlineDataSource {

    private final HeadlineDao headlineDao;

    public HeadlineLocalDataSource(HeadlineDao headlineDao) {
        this.headlineDao = headlineDao;
    }

    @Override
    public Observable<List<HeadlineModel>> getAll() {
        return headlineDao.getAll();
    }

    @Override
    public void insertHeadlineModel(HeadlineModel headlineModel) {
        headlineDao.insert(headlineModel);
    }

    @Override
    public void deleteHeadlineModel(HeadlineModel headlineModel) {
        headlineDao.delete(headlineModel);
    }

    @Override
    public void insertAll(List<HeadlineModel> headlineModelList) {
        headlineDao.insertAll(headlineModelList);
    }

    @Override
    public void deleteAll() {
        headlineDao.deleteAll();
    }
}
