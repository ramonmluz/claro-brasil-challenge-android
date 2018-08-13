package com.challenge.brasil.claro.moviesapp.model.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Entity
public class Movie {
    @PrimaryKey
    @NonNull
    protected String id;

    @ColumnInfo(name = "overview")
    protected String overview;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    protected String posterPath;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    protected String originalTitle;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    protected String voteAverage;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    protected String releaseDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
