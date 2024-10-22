package com.example.tarottales.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.Model.Zodiac;
import com.example.tarottales.R;
import com.example.tarottales.activity.LearnZodiacDetailActivity;

import java.util.List;

public class LearnZodiacAdapter extends RecyclerView.Adapter<LearnZodiacAdapter.ViewHolder> {

    List<Zodiac> listZodiac;

    public LearnZodiacAdapter(List<Zodiac> listZodiac) {
        this.listZodiac = listZodiac;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_zodiac_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(listZodiac.get(position));
    }

    @Override
    public int getItemCount() {
        return listZodiac != null ? listZodiac.size() : 0;
    }

    // view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLearnZodiacImage;

        void bindingView(){
            imgLearnZodiacImage = itemView.findViewById(R.id.imgLearnZodiacImage);
        }

        void bindingAction(){
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {
            Zodiac zodiac = listZodiac.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), LearnZodiacDetailActivity.class);
            intent.putExtra("zodiacId", zodiac.getId());
            view.getContext().startActivity(intent);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setData(Zodiac zodiac) {
            imgLearnZodiacImage.setImageResource(zodiac.getImage());
        }
    }
}
