package com.key.utpmatch.data.auth;

import com.key.utpmatch.models.auth.LoginRequest;
import com.key.utpmatch.models.auth.LoginResponse;
import com.key.utpmatch.models.auth.UserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Auth {
    String BASE_PATH = "auth/";

    // Ruta para el login
    @POST(BASE_PATH + "login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // Ruta para obtener el perfil del usuario
    @GET(BASE_PATH + "me")
    Call<UserProfileResponse> getUserProfile();
}
