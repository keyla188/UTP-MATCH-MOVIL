package com.key.utpmatch.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.key.utpmatch.DetailRecommentationActivity;
import com.key.utpmatch.R;
import com.key.utpmatch.models.ranking.RankingData;

import java.util.ArrayList;
import java.util.List;

public class TopMatchesAdapter extends RecyclerView.Adapter<TopMatchesAdapter.TopMatchViewHolder> {
    private List<RankingData> topMatches = new ArrayList<>(); // Inicializar para evitar null pointer

    public TopMatchesAdapter(List<RankingData> topMatches) {
        this.topMatches = topMatches;
    }

    @NonNull
    @Override
    public TopMatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflar el layout 'item_match'
        View view = inflater.inflate(R.layout.item_top, parent, false);
        return new TopMatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMatchViewHolder holder, int position) {
        RankingData match = topMatches.get(position);
        holder.nameTextView.setText(match.getName());
        holder.matchesTextView.setText(match.getTotal_matches() + " matches");

        // Usar Glide para cargar imágenes
        String imageUrl = match.getPhoto_url();
        Log.d("ImageURL", "URL: " + imageUrl);
        Glide.with(holder.profileImageView.getContext())
                .load(imageUrl)
                .into(holder.profileImageView);
        // Configuración del ImageButton (detail_icon)
        holder.detailIcon.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailRecommentationActivity.class);
            intent.putExtra("USER_ID", match.getUser_id()); // Pasar el ID del match
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return topMatches.size();
    }

    public void updateData(List<RankingData> newData) {
        if (newData != null) {
            this.topMatches.clear();
            this.topMatches.addAll(newData);
            notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
        }
    }

    public static class TopMatchViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, matchesTextView;
        ImageView profileImageView;

        ImageButton detailIcon;

        public TopMatchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            matchesTextView = itemView.findViewById(R.id.textViewMatches);
            profileImageView = itemView.findViewById(R.id.imageViewProfile);
            detailIcon = itemView.findViewById(R.id.detail_icon);
        }
    }
}
