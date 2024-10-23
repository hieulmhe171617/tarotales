package com.example.tarottales.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.Model.Planet;
import com.example.tarottales.R;
import com.example.tarottales.activity.LearnElementDetailActivity;
import com.example.tarottales.activity.LearnPlanetDetailActivity;

import java.util.List;

public class LearnPlanetAdapter extends RecyclerView.Adapter<LearnPlanetAdapter.ViewHolder> {

    List<Planet> listPlanet;

    public LearnPlanetAdapter(List<Planet> listPlanet) {
        this.listPlanet = listPlanet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_planet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(listPlanet.get(position));
    }

    @Override
    public int getItemCount() {
        return listPlanet != null ? listPlanet.size() : 0;
    }

    // view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLearnPlanetImage;

        void bindingView() {
            imgLearnPlanetImage = itemView.findViewById(R.id.imgLearnPlanetImage);
        }

        void bindingAction() {
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {
            Planet planet = listPlanet.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), LearnPlanetDetailActivity.class);
            intent.putExtra("planetId", planet.getId());
            view.getContext().startActivity(intent);
        }

        public void setData(Planet planet) {
            imgLearnPlanetImage.setImageResource(planet.getImage());
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }


    }
}
