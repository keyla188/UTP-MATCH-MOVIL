package com.key.utpmatch.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.key.utpmatch.R;
import com.key.utpmatch.models.match.MatchData;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchViewHolder> {
    private List<MatchData> matches;

    public MatchesAdapter(List<MatchData> matches) {
        this.matches = matches;
    }

    // Actualizar los datos del adaptador
    public void updateMatches(List<MatchData> newMatches) {
        this.matches.clear();
        this.matches.addAll(newMatches);
        notifyDataSetChanged(); // Notificar que los datos han cambiado
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflar el layout 'item_match'
        View view = inflater.inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        MatchData match = matches.get(position);

        if (match.getReceiver() != null) {
            holder.nameTextView.setText(match.getReceiver().getName());
            holder.careerTextView.setText(match.getReceiver().getDescription());

            // Obtener el número de teléfono del receptor
            String phoneNumber = match.getReceiver().getContact_phone(); // Asegúrate de que este campo esté en tu objeto

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
        } else {
            holder.nameTextView.setText("Desconocido");
            holder.careerTextView.setText("No disponible");
        }

        // Usar Glide para cargar la imagen desde la URL proporcionada
        String imageUrl = "https://ayuuqdrslybngxxlvqdh.supabase.co/storage/v1/object/public/photos/5a08b643-3e0d-4a75-9b91-6071df3a838e/2c1fa3b1-ca68-4ebc-b338-ada4cd2f17f9-thumbnail.jpeg";
        Glide.with(holder.imageView.getContext())
                .load(imageUrl) // Cargar la imagen desde la URL
                .into(holder.imageView); // Colocar la imagen en el ImageView
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, careerTextView;
        ImageView imageView;
        Button whatsappButton;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            careerTextView = itemView.findViewById(R.id.textViewCareer);
            imageView = itemView.findViewById(R.id.imageViewProfile);
            whatsappButton = itemView.findViewById(R.id.btnWhatsapp);
        }
    }
}
