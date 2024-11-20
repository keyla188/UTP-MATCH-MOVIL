package com.key.utpmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DetailRecommentationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recommentation);
        // Obtén el ID del usuario desde el Intent
        String userId = getIntent().getStringExtra("USER_ID");
        Toast.makeText(this, "ID: "+userId, Toast.LENGTH_SHORT).show();

        // Aquí puedes llamar a tu backend para obtener los datos
        //fetchUserDetails(userId);

    }
}