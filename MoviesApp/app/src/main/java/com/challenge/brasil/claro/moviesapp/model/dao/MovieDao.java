package com.challenge.brasil.claro.moviesapp.model.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.challenge.brasil.claro.moviesapp.model.vo.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Query("SELECT id FROM Movie as movie WHERE id == :movieId")
    String findSavedMovieId(String movieId);

    @Query("SELECT * FROM Movie WHERE UPPER (original_title) LIKE '%'|| UPPER(:movieSearch) || '%' ")
    List<Movie> findMoviesByTitle(String movieSearch);

//    @Insert
//    void insertAll(Movie... Movies);

    @Insert
    void insert(Movie Movie);

    @Delete
    void delete(Movie Movie);
}
