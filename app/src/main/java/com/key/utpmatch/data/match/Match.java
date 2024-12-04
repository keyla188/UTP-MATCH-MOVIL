package com.key.utpmatch.data.match;

import com.key.utpmatch.models.auth.UserProfileResponse;
import com.key.utpmatch.models.match.MyMatchResponse;
import com.key.utpmatch.models.match.RecommendationResponse;
import com.key.utpmatch.models.ranking.RankingResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Match {
    String BASE_PATH = "match/";

    // Ruta para obtener el perfil de recomendados
    @GET(BASE_PATH + "recommendations")
    Call<RecommendationResponse> getRecommendations(@Query("page") int page, @Query("limit") int limit);
    @GET(BASE_PATH + "my-matchs")
    Call<MyMatchResponse> getMyMatches(@Query("page") int page, @Query("limit") int limit);

    @GET(BASE_PATH + "my-pendings")
    Call<MyMatchResponse> getMyPendings(@Query("page") int page, @Query("limit") int limit);

    @GET(BASE_PATH + "ranking")
    Call<RankingResponse> getRanking();

    @POST(BASE_PATH + "create-request/" +"{id}")
    Call<ResponseBody> createRequest(@Path("id") String user_id);

    @POST(BASE_PATH + "accept-request/" +"{id}")
    Call<ResponseBody> acceptRequest(@Path("id") String match_id);

    @POST(BASE_PATH + "decline-request/" +"{id}")
    Call<ResponseBody> declineRequest(@Path("id") String match_id);
}
