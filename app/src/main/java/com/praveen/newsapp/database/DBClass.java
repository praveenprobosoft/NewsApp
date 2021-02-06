package com.praveen.newsapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EntityClass.class}, version = 1)
public abstract class DBClass extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
