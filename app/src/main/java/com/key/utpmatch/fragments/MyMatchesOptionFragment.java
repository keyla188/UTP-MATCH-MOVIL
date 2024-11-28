package com.key.utpmatch.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.key.utpmatch.R;
import com.key.utpmatch.adapters.MatchesAdapter;
import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.match.Match;
import com.key.utpmatch.data.user.User;
import com.key.utpmatch.models.match.MatchData;
import com.key.utpmatch.models.match.MyMatchResponse;
import com.key.utpmatch.models.match.RecommendationResponse;
import com.key.utpmatch.models.match.UserInfo;
import com.key.utpmatch.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyMatchesOptionFragment extends Fragment {


    private RecyclerView recyclerView;
    private MatchesAdapter matchesAdapter;
    private List<MatchData> matches;
    private Match matchService;
    private FrameLayout loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_matches_option, container, false);

        loader = view.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerViewMatches);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String token = PreferencesManager.getToken(getContext());
        if (token != null) {
            Retrofit retrofit = ApiClient.getClient(token);
            matchService = retrofit.create(Match.class);

            fetchMyMatches(1, 10);
        } else {
            Toast.makeText(getContext(), "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show();
        }

        matches = new ArrayList<>();
        matchesAdapter = new MatchesAdapter(matches);
        recyclerView.setAdapter(matchesAdapter);

        return view;
    }

    private void fetchMyMatches(int page, int limit) {
        matchService.getMyMatches(page, limit).enqueue(new Callback<MyMatchResponse>() {
            @Override
            public void onResponse(Call<MyMatchResponse> call, Response<MyMatchResponse> response) {
                if (response.isSuccessful()) {
                   MyMatchResponse myMatchResponse = response.body();
                    if (myMatchResponse != null && myMatchResponse.getData() != null && !myMatchResponse.getData().isEmpty()) {
                        // Actualizar la lista de matches con los datos obtenidos
                        matches.clear();
                        matches.addAll(myMatchResponse.getData());

                        matchesAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No se encontraron matches.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al obtener los datos.", Toast.LENGTH_SHORT).show();
                }
                loader.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MyMatchResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}