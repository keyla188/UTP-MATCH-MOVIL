package com.key.utpmatch.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.key.utpmatch.DetailRecommentationActivity;
import com.key.utpmatch.R;
import com.key.utpmatch.data.ApiClient;
import com.key.utpmatch.data.match.Match;
import com.key.utpmatch.models.match.MatchData;
import com.key.utpmatch.utils.PreferencesManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingsAdapter extends RecyclerView.Adapter<PendingsAdapter.MatchViewHolder> {
    private List<MatchData> matches;
    private boolean enableWhatsApp;

    public PendingsAdapter(List<MatchData> matches, boolean enableWhatsApp) {
        this.matches = matches;
        this.enableWhatsApp = enableWhatsApp;
    }

    // Actualizar los datos del adaptador
    public void updateMatches(List<MatchData> newMatches) {
        this.matches.clear();
        this.matches.addAll(newMatches);
        notifyDataSetChanged(); // Notificar que los datos han cambiado
    }

    @NonNull
    @Override
    public PendingsAdapter.MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflar el layout 'item_match'
        View view = inflater.inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingsAdapter.MatchViewHolder holder, int position) {
        MatchData match = matches.get(position);

        if (match.getReceiver() != null) {
            holder.nameTextView.setText(match.getTransmitter().getName());
            holder.careerTextView.setText(match.getTransmitter().getDescription());

            // Configurar visibilidad de los botones
            if (enableWhatsApp) {
                holder.whatsappButton.setVisibility(View.VISIBLE);
                holder.acceptButton.setVisibility(View.GONE);
                holder.declineButton.setVisibility(View.GONE);
            } else {
                holder.whatsappButton.setVisibility(View.GONE);
                holder.acceptButton.setVisibility(View.VISIBLE);
                holder.declineButton.setVisibility(View.VISIBLE);
            }

            // Obtener el número de teléfono del receptor
            String phoneNumber = match.getTransmitter().getContact_phone(); // Asegúrate de que este campo esté en tu objeto

            // Configurar el click listener para el botón de WhatsApp
            holder.whatsappButton.setOnClickListener(v -> {
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    // Abrir WhatsApp con el número
                    String url = "https://wa.me/" + phoneNumber; // Formato para WhatsApp
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.setPackage("com.whatsapp"); // Asegurarse de que se use WhatsApp
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "Número de teléfono no disponible", Toast.LENGTH_SHORT).show();
                }
            });

            // Botón aceptar
            holder.acceptButton.setOnClickListener(v -> handleMatchAction(match.getMatch_id(), "accept", holder));

            // Botón rechazar
            holder.declineButton.setOnClickListener(v -> handleMatchAction(match.getMatch_id(), "decline", holder));

        } else {
            holder.nameTextView.setText("Desconocido");
            holder.careerTextView.setText("No disponible");
        }

        // Usar Glide para cargar la imagen desde la URL proporcionada
        String imageUrl = match.getReceiver().getPhotos().get(0).getThumbnail_url();
        Glide.with(holder.imageView.getContext())
                .load(imageUrl) // Cargar la imagen desde la URL
                .into(holder.imageView); // Colocar la imagen en el ImageView

        // Configuración del ImageButton (detail_icon)
        holder.detailIcon.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailRecommentationActivity.class);
            intent.putExtra("USER_ID", match.getReceiver().getUser_id()); // Pasar el ID del match
            holder.itemView.getContext().startActivity(intent);
        });
    }

    private void handleMatchAction(String matchId, String action, MatchViewHolder holder) {
        Match matchService = ApiClient.getClient(PreferencesManager.getToken(holder.itemView.getContext())).create(Match.class);

        Call<ResponseBody> call;
        if ("accept".equals(action)) {
            call = matchService.acceptRequest(matchId);
        } else if ("decline".equals(action)) {
            call = matchService.declineRequest(matchId);
        } else {
            return;
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(holder.itemView.getContext(), "Acción realizada con éxito", Toast.LENGTH_SHORT).show();
                    // Eliminar el match de la lista después de aceptar o rechazar
                    matches.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Error en la acción", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(holder.itemView.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, careerTextView;
        ImageView imageView;
        Button whatsappButton, acceptButton, declineButton;
        ImageButton detailIcon;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            careerTextView = itemView.findViewById(R.id.textViewCareer);
            imageView = itemView.findViewById(R.id.imageViewProfile);
            whatsappButton = itemView.findViewById(R.id.btnWhatsapp);
            acceptButton = itemView.findViewById(R.id.btnaccept);
            declineButton = itemView.findViewById(R.id.btndecline);
            detailIcon = itemView.findViewById(R.id.detail_icon);
        }
    }
}
