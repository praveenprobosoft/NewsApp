package com.praveen.newsapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;
@Dao
public interface ArticleDao {
    @Query("SELECT * FROM bookmark")
    List<EntityClass> getAll();

    @Insert
    void insert(EntityClass articleModel);

    @Delete
    void delete(EntityClass articleModel);

}
