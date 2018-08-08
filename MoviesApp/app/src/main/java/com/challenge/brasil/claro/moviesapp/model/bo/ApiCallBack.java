package com.challenge.brasil.claro.moviesapp.model.bo;

import java.util.Map;

public interface ApiCallBack<T> {

    void onSuccess(T response);

    void onError(Map<Integer, String> error);
}
