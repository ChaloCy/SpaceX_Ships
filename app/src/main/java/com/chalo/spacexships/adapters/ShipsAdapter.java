package com.chalo.spacexships.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chalo.spacexships.R;
import com.chalo.spacexships.models.Ship;
import com.chalo.spacexships.views.ModalBottomSheet;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShipsAdapter extends RecyclerView.Adapter<ShipsAdapter.ViewHolder>{
    private final List<Ship> shipsList;
    public ArrayList<Ship> shipsArrayList;
    private final LayoutInflater inflater;
    private final Context context;

    public ShipsAdapter(Context context, List<Ship> shipsList){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.shipsList = shipsList;
        this.shipsArrayList = new ArrayList<>();
        this.shipsArrayList.addAll(shipsList);
    }


    @NonNull
    @Override
    public ShipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflater.inflate(R.layout.ship_main_list_item,
                parent, false);
        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipsAdapter.ViewHolder holder, int position) {
        Ship ship = shipsList.get(position);
        //set ship name to text view
        holder.shipName.setText(ship.getName());
        // set image to imageView using Glide library
        if( !ship.getImageUrl().isEmpty()){
            // Download photo from url and set to image view
            Glide.with(context).load(ship.getImageUrl()).into(holder.imageView);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call class to initialize modal bottom sheet
                ModalBottomSheet bottomSheet =  new ModalBottomSheet(ship.getHomePort(),
                       ship.getYearBuilt(), ship.getType(), ship.getImageUrl());
                //show bottom sheet
                bottomSheet.show(((AppCompatActivity) context).getSupportFragmentManager(), ModalBottomSheet.TAG);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shipsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public MaterialCardView cardView;
        public TextView shipName;

        public ImageView imageView;

        final ShipsAdapter adapter;

        public ViewHolder(@NonNull View itemView, ShipsAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.materialCardView);
            shipName = itemView.findViewById(R.id.nameTxt);
        }
    }

    /**
     * Method to filer ships by names
     * @param charText text from search EditText
     */
    public void filterShips(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        // Clear the initial list of ships
        shipsList.clear();
        if (charText.length() == 0) {
            // not search character entered, restore the initial list of ships before search
            shipsList.addAll(shipsArrayList);
        } else {
            for (Ship ship : shipsArrayList) {
                // If entered text match ship name, add ship to new list
                if (ship.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    shipsList.add(ship);
                }
            }
        }
        // Tell adapter that the list has changed
        notifyDataSetChanged();
    }
}
