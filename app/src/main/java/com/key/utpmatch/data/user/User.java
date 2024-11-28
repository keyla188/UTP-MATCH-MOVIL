package com.key.utpmatch.data.user;

import com.key.utpmatch.models.match.RecommendationResponse;
import com.key.utpmatch.models.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface User {

    String BASE_PATH = "user/";

    @GET(BASE_PATH + "{id}")
    Call<UserResponse> getUserDetail(@Path("id") String userId);
}
