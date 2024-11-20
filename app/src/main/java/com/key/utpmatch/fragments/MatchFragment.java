package com.key.utpmatch.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.key.utpmatch.DetailRecommentationActivity;
import com.key.utpmatch.R;
import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.match.Match;
import com.key.utpmatch.models.match.Interest;
import com.key.utpmatch.models.match.RecommendationResponse;
import com.key.utpmatch.models.match.UserRecomendation;
import com.key.utpmatch.utils.PreferencesManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MatchFragment extends Fragment {

    private ChipGroup chipGroupInterests;
    private FrameLayout contentContainer;
    private LinearLayout buttonContainer;
    private FrameLayout loader;
    private ImageView personImage;
    private TextView personName;
    private TextView personAge;
    private TextView personCareer;
    private TextView personCampus;
    private TextView playingSong;
    private List<UserRecomendation> recommendations;
    private UserRecomendation currentRecommendation;
    private int currentIndex = 0;

    private Match matchService;
    private MediaPlayer mediaPlayer;
    private final String songUrl = "https://cdnt-preview.dzcdn.net/api/1/1/4/e/8/0/4e86955ec47c15f985b3c164d24355ad.mp3?hdnea=exp=1731640517~acl=/api/1/1/4/e/8/0/4e86955ec47c15f985b3c164d24355ad.mp3*~data=user_id=0,application_id=42~hmac=2bd7056e3096cd1152398cc1badf046408c78880475831244b49046f5ff30a00";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);


        chipGroupInterests = view.findViewById(R.id.chipGroupInterests);
        contentContainer = view.findViewById(R.id.content_container);
        buttonContainer = view.findViewById(R.id.button_container);
        loader = view.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        personImage = view.findViewById(R.id.person_image);
        personName = view.findViewById(R.id.person_name);
        personAge = view.findViewById(R.id.person_age);
        personCareer = view.findViewById(R.id.person_career);
        personCampus = view.findViewById(R.id.person_campus);
        playingSong = view.findViewById(R.id.playing_song);

        // LO DE BLOCK DE NOTAS
        ImageButton detailIcon = view.findViewById(R.id.detail_icon);
        detailIcon.setOnClickListener(v -> {
            if (currentRecommendation != null) {
                String userId = currentRecommendation.getUser_id();
                Intent intent = new Intent(getContext(), DetailRecommentationActivity.class);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            } else {
                Log.e("DetailIconClick", "currentRecommendation es nulo");
                Toast.makeText(getContext(), "No hay recomendación actual", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton nextButton = view.findViewById(R.id.accept_button);

        nextButton.setOnClickListener(v -> showNextRecommendation());

        ImageView playIcon = view.findViewById(R.id.play_icon);

        // Cargar el GIF usando Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.play)
                .into(playIcon);

        // Verifica si el token está disponible antes de hacer la llamada
        String token = PreferencesManager.getToken(getContext());
        if (token != null) {
            // Obtén la instancia de Retrofit desde ApiClient
            Retrofit retrofit = ApiClient.getClient(token);

            // Crea el servicio de coincidencias
            matchService = retrofit.create(Match.class);

            // Realiza la solicitud de recomendaciones
            fetchRecommendations(1, 10);
        } else {
            // Maneja el caso cuando no hay token (por ejemplo, redirigir a login o mostrar un mensaje)
            Toast.makeText(getContext(), "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show();
        }

        playSong(songUrl);



        return view;
    }

    private void fetchRecommendations(int page, int limit) {
        matchService.getRecommendations(page, limit).enqueue(new Callback<RecommendationResponse>() {
            @Override
            public void onResponse(Call<RecommendationResponse> call, Response<RecommendationResponse> response) {
                if (response.isSuccessful()) {
                    RecommendationResponse recommendationResponse = response.body();
                    if (recommendationResponse != null && !recommendationResponse.getData().isEmpty()) {
                        recommendations = recommendationResponse.getData();
                        currentIndex = 0; // Inicia desde la primera recomendación
                        showRecommendation(currentIndex); // Muestra la primera recomendación
                        loader.setVisibility(View.GONE);
                        contentContainer.setVisibility(View.VISIBLE);
                        buttonContainer.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Manejar el error
                }
            }

            @Override
            public void onFailure(Call<RecommendationResponse> call, Throwable t) {
                // Manejar el fallo en la llamada
            }
        });
    }

    private void showRecommendation(int index) {
        // Verifica si el fragmento está adjunto a la actividad y que el contexto no es null
        if (getActivity() != null && isAdded() && recommendations != null && index < recommendations.size()) {
            UserRecomendation recommendation = recommendations.get(index);
            currentRecommendation = recommendation;
            if (recommendation.getPhotos() != null && !recommendation.getPhotos().isEmpty()) {
                String firstPhotoUrl = recommendation.getPhotos().get(0).getFile_url();

                // Usa Glide de forma segura con el contexto
                Glide.with(this) // Usa 'this' para el contexto del fragmento
                        .load(firstPhotoUrl)
                        .into(personImage);
            } else {
                // Opcional: establece una imagen por defecto si no hay fotos
                // personImage.setImageResource(R.drawable.placeholder_image);
            }

            // Actualiza los textos
            personName.setText(recommendation.getName());
            //personAge.setText(recommendation.getAge() + " años");
            personCareer.setText(recommendation.getCareer().getName());
            personCampus.setText(recommendation.getCampus().getName());
            //playingSong.setText(recommendation.getFavoriteSong());
            populateChipGroup(chipGroupInterests, recommendation.getInterests());
        } else {
            Log.e("MatchFragment", "Fragmento no está adjunto o contexto es nulo.");
        }
    }


    private void showNextRecommendation() {
        if (recommendations != null && !recommendations.isEmpty()) {
            currentIndex = (currentIndex + 1) % recommendations.size(); // Vuelve al inicio si alcanza el final
            showRecommendation(currentIndex);
        }
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
            Toast.makeText(getContext(), "Error al cargar la canción", Toast.LENGTH_SHORT).show();
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

    private void populateChipGroup(ChipGroup chipGroup, List<Interest> interests) {
        chipGroup.removeAllViews();
        for (Interest interest : interests) {
            Chip chip = new Chip(getContext());
            chip.setText(interest.getName());
            chip.setTextColor(getResources().getColor(android.R.color.white));
            chip.setChipBackgroundColorResource(R.color.semi_transparent_black);
            chip.setChipCornerRadius(50f);
            chip.setChipStrokeColorResource(android.R.color.transparent);
            chipGroup.addView(chip);
        }
    }


}