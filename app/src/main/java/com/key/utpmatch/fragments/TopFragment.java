package com.key.utpmatch.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.key.utpmatch.R;
import com.key.utpmatch.adapters.MatchesAdapter;
import com.key.utpmatch.adapters.TopMatchesAdapter;
import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.match.Match;
import com.key.utpmatch.models.match.MyMatchResponse;
import com.key.utpmatch.models.ranking.RankingData;
import com.key.utpmatch.models.ranking.RankingResponse;
import com.key.utpmatch.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TopFragment extends Fragment {
    private RecyclerView recyclerView;
    private TopMatchesAdapter adapter;
    private List<RankingData> topMatches;
    private Match matchService;
    private FrameLayout loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        loader = view.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerViewTopMatches);

        // Configurar el GridLayoutManager con 2 columnas
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        String token = PreferencesManager.getToken(getContext());
        if (token != null) {
            Retrofit retrofit = ApiClient.getClient(token);
            matchService = retrofit.create(Match.class);

            fetchRanking();
        } else {
            Toast.makeText(getContext(), "Por favor, inicie sesión primero.", Toast.LENGTH_SHORT).show();
        }

        topMatches = new ArrayList<>();
        adapter = new TopMatchesAdapter(topMatches);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void fetchRanking() {
        matchService.getRanking().enqueue(new Callback<RankingResponse>() {
            @Override
            public void onResponse(Call<RankingResponse> call, Response<RankingResponse> response) {
                if (response.isSuccessful()) {
                    RankingResponse rankingMatchResponse = response.body();
                    if (rankingMatchResponse != null && rankingMatchResponse.getData() != null && !rankingMatchResponse.getData().isEmpty()) {
                        // Actualizar la lista de matches con los datos obtenidos
                        topMatches.clear();
                        topMatches.addAll(rankingMatchResponse.getData());

                        adapter.notifyDataSetChanged();
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
            public void onFailure(Call<RankingResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}