package com.example.tarottales.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.R;
import com.example.tarottales.adapter.TopicHistoryAdapter;
import com.example.tarottales.dto.TopicHistoryDTO;
import com.example.tarottales.file.JsonTopicHistoryDTOHelper;

import java.util.ArrayList;
import java.util.List;

public class TopicHistoryActivity extends AppCompatActivity {
    private RecyclerView rcvHistory;
    private TopicHistoryAdapter adapter;
    private ImageView ivBack;
    private List<TopicHistoryDTO> dtoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        checkNumberOfItems();
        bindingDataToRecyclerView();
    }

    private void checkNumberOfItems() {
        //luu toi da 10 lan gan nhat
        dtoList = JsonTopicHistoryDTOHelper.readTopicHistoryDToFromJson(this, "topicHistory.json");
        if (dtoList == null) {
            dtoList = new ArrayList<>();
        } else if (dtoList.size() > 10) {
            removeOutOfTenItems();
        }
    }


    private void removeOutOfTenItems() {
        dtoList = dtoList.subList(0, 10);
        JsonTopicHistoryDTOHelper.saveTopicHistoryDTOToJson(this, dtoList, "topicHistory.json");
    }

    private void bindingDataToRecyclerView() {
        adapter = new TopicHistoryAdapter(dtoList);
        rcvHistory.setAdapter(adapter);
        rcvHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindingAction() {
        ivBack.setOnClickListener(this::onClickIvBack);
    }

    private void onClickIvBack(View view) {
        finish();
    }

    private void bindingView() {
        rcvHistory = findViewById(R.id.rcvHistory);
        ivBack = findViewById(R.id.ivBack);

    }
}