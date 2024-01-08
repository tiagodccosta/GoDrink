package com.incheymus.godrink;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<Place> places;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public PlacesAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
    }

    public void removeDuplicatePlaces(List<Place> newPlaces) {
        Set<String> uniquePlaceNames = new HashSet<>();

        Iterator<Place> iterator = places.iterator();
        while (iterator.hasNext()) {
            Place existingPlace = iterator.next();
            uniquePlaceNames.add(existingPlace.getPlaceName());
        }

        iterator = newPlaces.iterator();
        while (iterator.hasNext()) {
            Place newPlace = iterator.next();
            if (uniquePlaceNames.contains(newPlace.getPlaceName())) {
                iterator.remove();
            } else {
                uniquePlaceNames.add(newPlace.getPlaceName());
            }
        }

        int startPosition = places.size();
        places.addAll(newPlaces);
        notifyItemRangeInserted(startPosition, newPlaces.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;
        TextView placeAddress;
        ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(places.get(position));
                    }
                }
            });

            placeImage = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.namePLACEHOLDER);
            placeAddress = itemView.findViewById(R.id.addressPLACEHOLDER);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_places_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);

        holder.placeName.setText(place.getPlaceName());
        holder.placeAddress.setText(place.getPlaceAddress());

        byte[] placeImage = place.getPlaceImage();

        // Use Picasso to load and display the image
        if (placeImage != null && placeImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(placeImage, 0, placeImage.length);
            holder.placeImage.setImageBitmap(bitmap);
        } else {
            // Handle the case when the image is not available
            holder.placeImage.setImageResource(R.drawable.afterparty);
        }
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Place place);
    }
}
