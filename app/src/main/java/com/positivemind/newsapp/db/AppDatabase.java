package com.positivemind.newsapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.positivemind.newsapp.db.headline.HeadlineDao;
import com.positivemind.newsapp.headline.HeadlineModel;


@Database(entities = {HeadlineModel.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract HeadlineDao headlineDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "NewsApp.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
