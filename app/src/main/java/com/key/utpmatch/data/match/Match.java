package com.key.utpmatch.data.match;

import com.key.utpmatch.models.auth.UserProfileResponse;
import com.key.utpmatch.models.match.RecommendationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Match {
    String BASE_PATH = "match/";

    // Ruta para obtener el perfil de recomendados
    @GET(BASE_PATH + "recommendations")
    Call<RecommendationResponse> getRecommendations(@Query("page") int page, @Query("limit") int limit);
}
