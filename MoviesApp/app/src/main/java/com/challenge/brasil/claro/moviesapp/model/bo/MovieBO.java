package com.challenge.brasil.claro.moviesapp.model.bo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.challenge.brasil.claro.moviesapp.R;
import com.challenge.brasil.claro.moviesapp.model.vo.Movie;
import com.challenge.brasil.claro.moviesapp.util.JsonUtil;
import com.challenge.brasil.claro.moviesapp.util.ViewUtil;
import com.challenge.brasil.claro.moviesapp.view.MainActivity_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EBean(scope = EBean.Scope.Singleton)
public class MovieBO {

    private static final String TAG = MovieBO.class.getSimpleName();

    @RootContext
    Context context;

    @Background
    public void requestMovies(final ApiCallBack callBack) {
        final Map<Integer, String> responseMap = new HashMap<Integer, String>();

        String searchMovieType  = ViewUtil.getSharedPreferences(context);
        Uri uri = buildMoviesUri(searchMovieType);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String items = response.getString("results");
                            List<Movie> movies = JsonUtil.parseJsonMovieList(items);
                            callBack.onSuccess(movies);
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                            responseMap.put(-1, "");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String errorMessage = "";
                        NetworkResponse response = error.networkResponse;

                        if (response != null) {
                            errorMessage = validateMessagStatusCodeError(error, errorMessage, response);
                        }

                        if (!TextUtils.isEmpty(errorMessage)) {
                            responseMap.put(response.statusCode, errorMessage);
                        } else {
                            responseMap.put(-1, "");
                        }
                        callBack.onError(responseMap);
                    }
                });

        VolleyRequest.getInstance(context).addToRequestQueue(request);
    }

    private String validateMessagStatusCodeError(VolleyError error, String errorMessage, NetworkResponse response) {

        switch (response.statusCode) {
            case 404: // not found
            case 422:
            case 400: // bad request
            case 401:

                try {
                    String string = new String(error.networkResponse.data);
                    JSONObject object = new JSONObject(string);
                    if (object.has("message")) {
                        errorMessage = object.get("message").toString();
                        Log.d(TAG, errorMessage);

                    } else if (object.has("error_description")) {
                        errorMessage = object.get("error_description").toString();
                        Log.d(TAG, errorMessage);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Couldn't do parser");
                }
                break;
            default:
                Log.d(TAG, "Status code not found");
        }
        return errorMessage;
    }

    private Uri buildMoviesUri(String searchMovieType) {
        return Uri.parse(context.getString(R.string.SEARCH_MOVIE_URL))
                .buildUpon()
                .appendPath(searchMovieType)
                .appendQueryParameter(context.getString(R.string.api_key), context.getString(R.string.KEY_MOVIE_DB))
                .appendQueryParameter(context.getString(R.string.language), context.getString(R.string.LANGUAGE_VALUE)).build();
    }


}