package com.anju.artinstituteofchicago;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public  class ArtworkViewHolder extends RecyclerView.ViewHolder {
    private final ImageView artworkImageView;
    private final TextView titleTextView;

    public ArtworkViewHolder(@NonNull View itemView) {
        super(itemView);
        artworkImageView = itemView.findViewById(R.id.artwork_image_view);
        titleTextView = itemView.findViewById(R.id.title_text_view);
    }

    public void bind(Artwork artwork) {
        titleTextView.setText(artwork.getTitle());
        // Load image using Picasso library
        Picasso.get().load(artwork.getThumbnailImageUrl())
                .fit()
                .centerCrop()
                .error(R.drawable.error_placeholder)
                .into(artworkImageView);
    }
}
