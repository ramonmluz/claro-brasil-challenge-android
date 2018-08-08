package com.challenge.brasil.claro.moviesapp.util;

import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.parceler.Repository;

import java.util.HashMap;
import java.util.List;

public class JsonUtil {
    private static Gson gson;

    public static Gson getGson() {

        if( gson == null){
            gson = new Gson();
        }
        return gson;
    }

    public static HashMap<String, Object> parseJson(Object src) {
        String jsonString = getGson().toJson(src);

        return  getGson().fromJson(jsonString, new TypeToken<HashMap<String,Object>>() {}.getType());
    }

    public static List<Movie> parseJsonMovieList(String jsonString) {
        return getGson().fromJson(jsonString, new TypeToken <List<Movie>>() {}.getType());
    }
}
