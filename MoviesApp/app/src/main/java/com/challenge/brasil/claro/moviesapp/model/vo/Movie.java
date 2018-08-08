package com.challenge.brasil.claro.moviesapp.model.vo;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Movie {

    protected String id;
    protected String overview;

    @SerializedName("poster_path")
    protected String posterPath;
    @SerializedName("original_title")
    protected String originalTitle;
    @SerializedName("vote_average")
    protected String voteAverage;
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
