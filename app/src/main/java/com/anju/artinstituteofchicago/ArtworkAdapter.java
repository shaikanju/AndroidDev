package com.anju.artinstituteofchicago;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkViewHolder> {

    private List<Artwork> artworkList;

    public ArtworkAdapter(List<Artwork> artworkList) {
        this.artworkList = artworkList;
    }

    @NonNull
    @Override
    public ArtworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_item_main, parent, false);
        return new ArtworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        holder.bind(artwork);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArtActivity.class);
                intent.putExtra("title", artwork.getTitle());
                intent.putExtra("date_display", artwork.getDateDisplay());
                intent.putExtra("artist_display", artwork.getArtistDisplay());
                intent.putExtra("medium_display", artwork.getMediumDisplay());
                intent.putExtra("artwork_type_title", artwork.getArtworkTypeTitle());
                intent.putExtra("image_id", artwork.getImageId());
                intent.putExtra("dimensions", artwork.getDimensions());
                intent.putExtra("department_title", artwork.getDepartmentTitle());
                intent.putExtra("credit_line", artwork.getCreditLine());
                intent.putExtra("place_of_origin", artwork.getPlaceOfOrigin());
                intent.putExtra("gallery_title", artwork.getGalleryTitle());
                intent.putExtra("gallery_id", artwork.getGalleryId());
                intent.putExtra("id", artwork.getId());
                intent.putExtra("api_link", artwork.getApiLink());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }


}


