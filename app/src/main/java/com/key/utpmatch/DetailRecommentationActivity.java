package com.key.utpmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.user.User;
import com.key.utpmatch.models.interest.Interest;
import com.key.utpmatch.models.photo.Photo;
import com.key.utpmatch.models.user.UserDetail;
import com.key.utpmatch.models.user.UserResponse;
import com.key.utpmatch.utils.PreferencesManager;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailRecommentationActivity extends AppCompatActivity {

    List<CarouselItem> list = new ArrayList<>();

    private UserDetail userDetail;

    private ImageButton backIcon;
    private ImageCarousel carousel;
    private TextView userName;
    private TextView userAge;
    private ChipGroup chipGroupInterests;
    private TextView userCareer;
    private TextView userCampus;
    private TextView userZodiac;
    private TextView userSong;
    private TextView userDescription;
    private FrameLayout loader;
    private LinearLayout contentContainer;
    private User userDetailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recommentation);

        backIcon = findViewById(R.id.back_icon);
        carousel = findViewById(R.id.carousel);
        userName = findViewById(R.id.user_name);
        userAge = findViewById(R.id.user_age);
        chipGroupInterests = findViewById(R.id.chipGroupInterests);
        userCareer = findViewById(R.id.user_career);
        userCampus = findViewById(R.id.user_campus);
        userZodiac = findViewById(R.id.user_zodiac);
        userSong = findViewById(R.id.user_song);
        userDescription = findViewById(R.id.user_description);
        loader = findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        contentContainer = findViewById(R.id.content_container);
        contentContainer.setVisibility(View.GONE);

        ImageButton backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> finish());

        // Obtén el ID del usuario desde el Intent
        String userId = getIntent().getStringExtra("USER_ID");

        // Verifica si el token está disponible antes de hacer la llamada
        String token = PreferencesManager.getToken(this);
        if (token != null) {
            // Obtén la instancia de Retrofit desde ApiClient
            Retrofit retrofit = ApiClient.getClient(token);
            userDetailService = retrofit.create(User.class);

            // Realiza la solicitud de detalles de usuario
            fetchUserDetails(userId);
        } else {
            // Maneja el caso cuando no hay token (por ejemplo, redirigir a login o mostrar un mensaje)
            Toast.makeText(this, "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show();
        }

        ImageCarousel carousel = findViewById(R.id.carousel);
    }

    private void fetchUserDetails(String userId) {
        userDetailService.getUserDetail(userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    Log.d("DetailRecommentation", "Respuesta exitosa: " + userResponse);
                    if (userResponse != null) {
                        userDetail = userResponse.getData();
                        Toast.makeText(DetailRecommentationActivity.this, "Hola"+userDetail.getName(), Toast.LENGTH_SHORT).show();
                        showUserDetails(userDetail);
                        Log.d("DetailRecommentation", "Detalles del usuario: " + userResponse.getData());
                    }
                } else {
                    // Manejar el error
                    Log.e("DetailRecommentation", "Error en la respuesta: " + response.message());
                    Toast.makeText(DetailRecommentationActivity.this, "Error al obtener detalles del usuario", Toast.LENGTH_SHORT).show();
                }
                loader.setVisibility(View.GONE);
                contentContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Manejar el fallo en la llamada
                Log.e("DetailRecommentation", "Error en la llamada: " + t.getMessage());
                loader.setVisibility(View.GONE);
            }
        });
    }


    private void showUserDetails(UserDetail userDetail) {
        // Mostrar los detalles del usuario
        userName.setText(userDetail.getName());
        //personAge.setText(userDetail.getAge() + " años");
        userCareer.setText(userDetail.getCareer().getName());
        userCampus.setText(userDetail.getCampus().getName());
        //personZodiac.setText(userDetail.getZodiac());
        //personSong.setText(recommendation.getFavoriteSong());
        userDescription.setText(userDetail.getDescription());

        // Si hay fotos, cargarlas
        if (userDetail.getPhotos() != null && !userDetail.getPhotos().isEmpty()) {
            // Recorrer las fotos y agregarlas a la lista
            for (Photo photo : userDetail.getPhotos()) {
                // Agregar cada foto como un nuevo CarouselItem
                list.add(new CarouselItem(photo.getFile_url()));
            }
            carousel.setData(list);
        }

        // Llenar el grupo de chips con los intereses
        //populateChipGroup(chipGroupInterests, userDetail.getInterests());
    }

    private void populateChipGroup(ChipGroup chipGroup, List<Interest> interests) {
        chipGroup.removeAllViews();
        for (Interest interest : interests) {
            Chip chip = new Chip(getBaseContext());
            chip.setText(interest.getName());
            chip.setTextColor(getResources().getColor(android.R.color.white));
            chip.setChipBackgroundColorResource(R.color.semi_transparent_black);
            chip.setChipCornerRadius(50f);
            chip.setChipStrokeColorResource(android.R.color.transparent);
            chipGroup.addView(chip);
        }
    }
}