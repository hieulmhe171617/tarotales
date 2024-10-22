package com.example.tarottales.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.R;
import com.example.tarottales.activity.DetailsOneItemInHistoryActivity;
import com.example.tarottales.dto.TopicHistoryDTO;

import java.util.List;

public class TopicHistoryAdapter extends RecyclerView.Adapter<TopicHistoryAdapter.TopicHistoryViewHolder> {

    private List<TopicHistoryDTO> list;

    public TopicHistoryAdapter(List<TopicHistoryDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TopicHistoryAdapter.TopicHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new TopicHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHistoryAdapter.TopicHistoryViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class TopicHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDay, tvMonth, tvYear;
        private TextView tvCard1, tvCard2, tvCard3;
        private ImageView ivCard1, ivCard2, ivCard3;

        public TopicHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
            bindingAction();
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
        }

        private void onClickItem(View view) {
            Intent intent = new Intent(itemView.getContext(), DetailsOneItemInHistoryActivity.class);
            intent.putExtra("topicHistoryDTO", list.get(getAdapterPosition()));
            itemView.getContext().startActivity(intent);
        }

        private void bindingView() {
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvCard1 = itemView.findViewById(R.id.tvCard1);
            tvCard2 = itemView.findViewById(R.id.tvCard2);
            tvCard3 = itemView.findViewById(R.id.tvCard3);
            ivCard1 = itemView.findViewById(R.id.ivCard1);
            ivCard2 = itemView.findViewById(R.id.ivCard2);
            ivCard3 = itemView.findViewById(R.id.ivCard3);
        }

        public void setData(TopicHistoryDTO topicHistoryDTO) {
            String date = topicHistoryDTO.getDate();
            String[] dateSplit = date.split("-");
            tvDay.setText(dateSplit[0]);
            tvMonth.setText("Tháng " + dateSplit[1]);
            tvYear.setText(dateSplit[2]);
            tvCard1.setText(topicHistoryDTO.getCard1().getName());
            tvCard2.setText(topicHistoryDTO.getCard2().getName());
            tvCard3.setText(topicHistoryDTO.getCard3().getName());
            ivCard1.setImageResource(topicHistoryDTO.getCard1().getImage());
            ivCard2.setImageResource(topicHistoryDTO.getCard2().getImage());
            ivCard3.setImageResource(topicHistoryDTO.getCard3().getImage());
        }
    }
}
