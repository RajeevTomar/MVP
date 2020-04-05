package com.positivemind.newsapp.db.headline;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.positivemind.newsapp.headline.HeadlineModel;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface HeadlineDao {

    @Query("SELECT * FROM headline ORDER BY publishedAt DESC")
    Observable<List<HeadlineModel>> getAll();

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param headlineModel the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HeadlineModel headlineModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HeadlineModel> headlineModelList);

    @Delete
    void delete(HeadlineModel headlineModel);

    @Query("DELETE FROM headline")
    void deleteAll();



}
