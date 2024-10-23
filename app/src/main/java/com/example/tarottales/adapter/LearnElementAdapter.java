package com.example.tarottales.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.Model.Element;
import com.example.tarottales.R;
import com.example.tarottales.activity.LearnElementDetailActivity;

import java.util.List;

public class LearnElementAdapter extends RecyclerView.Adapter<LearnElementAdapter.ViewHolder>{

    List<Element> listElement;

    public LearnElementAdapter(List<Element> listElement) {
        this.listElement = listElement;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_element_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(listElement.get(position));
    }

    @Override
    public int getItemCount() {
        return listElement != null ? listElement.size() : 0;
    }

    // View holder class
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgLearnElementImage;

        void bindingView(){
            imgLearnElementImage = itemView.findViewById(R.id.imgLearnElementImage);
        }
        void bindingAction(){
            itemView.setOnClickListener(this::onItemViewClick);
        }

        private void onItemViewClick(View view) {
            Element element = listElement.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), LearnElementDetailActivity.class);
            intent.putExtra("elementId", element.getId());
            view.getContext().startActivity(intent);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        public void setData(Element element) {
            imgLearnElementImage.setImageResource(element.getImage());
        }
    }

}
