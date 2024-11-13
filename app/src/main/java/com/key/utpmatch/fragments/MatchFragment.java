package com.key.utpmatch.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.key.utpmatch.R;
import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.auth.Auth;
import com.key.utpmatch.data.match.Match;
import com.key.utpmatch.models.match.RecommendationResponse;
import com.key.utpmatch.models.match.UserRecomendation;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MatchFragment extends Fragment {

    private Match matchService;
    private MediaPlayer mediaPlayer;
    private String songUrl = "https://cdnt-preview.dzcdn.net/api/1/1/4/e/8/0/4e86955ec47c15f985b3c164d24355ad.mp3?hdnea=exp=1731432015~acl=/api/1/1/4/e/8/0/4e86955ec47c15f985b3c164d24355ad.mp3*~data=user_id=0,application_id=42~hmac=920fd4403d26b8f2620142974507196ffb879a86b5b11ba36f31c66b85057d98";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        ImageView playIcon = view.findViewById(R.id.play_icon);

        // Cargar el GIF usando Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.play_gif)
                .into(playIcon);

        playSong(songUrl);

        // Obtén la instancia de Retrofit desde ApiClient
        Retrofit retrofit = ApiClient.getClient();

        // Crea el servicio de autenticación
        matchService = retrofit.create(Match.class);
        fetchRecommendations(1, 10);


        return view;
    }

    private void fetchRecommendations(int page, int limit) {
        matchService.getRecommendations(page, limit).enqueue(new Callback<RecommendationResponse>() {
            @Override
            public void onResponse(Call<RecommendationResponse> call, Response<RecommendationResponse> response) {
                if (response.isSuccessful()) {
                    // Manejar la respuesta
                    RecommendationResponse recommendationResponse = response.body();
                    if (recommendationResponse != null) {
                        List<UserRecomendation> recommendations = recommendationResponse.getData();
                        // Actualiza la UI con las recomendaciones
                    }
                } else {
                    // Manejar error
                }
            }

            @Override
            public void onFailure(Call<RecommendationResponse> call, Throwable t) {
                // Manejar el fallo en la llamada
            }
        });
    }

    // Método para inicializar y reproducir la canción
    private void playSong(String songUrl) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(songUrl); // Configura la URL
            mediaPlayer.prepareAsync(); // Prepara la reproducción de manera asíncrona
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start(); // Inicia la reproducción cuando esté listo
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Detener el reproductor cuando el fragmento se pausa
    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Liberar el MediaPlayer al destruir la vista del fragmento
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}