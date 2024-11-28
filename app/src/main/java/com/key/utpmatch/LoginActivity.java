package com.key.utpmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.auth.Auth;
import com.key.utpmatch.models.auth.LoginRequest;
import com.key.utpmatch.models.auth.LoginResponse;
import com.key.utpmatch.utils.PreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button);

        loginButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        loginButton.setEnabled(false);
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validación simple de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true);
            return;
        }


        LoginRequest loginRequest = new LoginRequest(email, password);


        String token = PreferencesManager.getToken(LoginActivity.this);


        Retrofit retrofit = ApiClient.getClient(token);

        // Crea el servicio de autenticación
        Auth authService = retrofit.create(Auth.class);

        // Realiza la solicitud de inicio de sesión
        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    PreferencesManager.saveToken(LoginActivity.this, token);
                    String tokeen = PreferencesManager.getToken(LoginActivity.this);
                    //saveToken(token);  // Guarda el token en SharedPreferences
                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    // Redirige al usuario a la pantalla principal
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error en las credenciales", Toast.LENGTH_SHORT).show();
                    loginButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}