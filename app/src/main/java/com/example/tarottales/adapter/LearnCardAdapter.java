package com.example.tarottales.adapter;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.activity.LearnCardDetailActivity;

import java.util.List;

public class LearnCardAdapter extends RecyclerView.Adapter<LearnCardAdapter.ViewHolder> {

    // instance variables
    List<TarotCard> listCard;

    public LearnCardAdapter(List<TarotCard> listCard) {
        this.listCard = listCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_card_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(listCard.get(position));
    }

    @Override
    public int getItemCount() {
        return listCard != null ? listCard.size() : 0;
    }

    // View holder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        // <editor-fold desc="Binding">
        ImageView imgLearnCardImage;
        TextView tvLearnCardName;
        void bindingView(){
            imgLearnCardImage = itemView.findViewById(R.id.imgLearnCardImage);
            tvLearnCardName = itemView.findViewById(R.id.tvLearnCardName);
        }
        void bindingAction(){
            itemView.setOnClickListener(this::onItemViewClick);
        }
        // </editor-fold>

        // <editor-fold desc="Action Method">

        // item click
        private void onItemViewClick(View view) {
            TarotCard tarotCard = listCard.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), LearnCardDetailActivity.class);
            intent.putExtra("cardId", tarotCard.getId());
            view.getContext().startActivity(intent);
        }

        // </editor-fold>

        // view holder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        // set up data method
        public void setData(TarotCard tarotCard) {
            imgLearnCardImage.setImageResource(tarotCard.getImage());
            tvLearnCardName.setText(tarotCard.getName());
        }
    }

}
