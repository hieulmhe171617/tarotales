package com.example.tarottales.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarottales.R;
import com.example.tarottales.dto.TopicHistoryDTO;

public class DetailsOneItemInHistoryActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvCard1, tvCard2, tvCard3;
    private ImageView ivCard1, ivCard2, ivCard3;
    private TextView tvNote;
    private TopicHistoryDTO historyItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_one_item_in_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        receiveDateFromIntent();
    }

    private void receiveDateFromIntent() {
        historyItem = (TopicHistoryDTO) getIntent().getSerializableExtra("topicHistoryDTO");
        bindingDataToView();
    }

    private void bindingDataToView() {
        String note = "Ghi chú: " + historyItem.getDate() + " (" + historyItem.getTime().substring(0,5) + ")\n" + historyItem.getNote();
        tvCard1.setText(historyItem.getCard1().getName());
        tvCard2.setText(historyItem.getCard2().getName());
        tvCard3.setText(historyItem.getCard3().getName());
        ivCard1.setImageResource(historyItem.getCard1().getImage());
        ivCard2.setImageResource(historyItem.getCard2().getImage());
        ivCard3.setImageResource(historyItem.getCard3().getImage());
        tvNote.setText(note);
    }

    private void bindingAction() {
        ivBack.setOnClickListener(this::onClickBack);
        ivCard1.setOnClickListener(this::onClickOpenCard);
        ivCard2.setOnClickListener(this::onClickOpenCard);
        ivCard3.setOnClickListener(this::onClickOpenCard);
    }

    private void onClickOpenCard(View view) {
        //mo sang intent chi tiet
    }

    private void onClickBack(View view) {
        finish();
    }

    private void bindingView() {
        ivBack = findViewById(R.id.ivBack);
        tvCard1 = findViewById(R.id.tvCard1);
        tvCard2 = findViewById(R.id.tvCard2);
        tvCard3 = findViewById(R.id.tvCard3);
        ivCard1 = findViewById(R.id.ivCard1);
        ivCard2 = findViewById(R.id.ivCard2);
        ivCard3 = findViewById(R.id.ivCard3);
        tvNote = findViewById(R.id.tvNote);
    }
}