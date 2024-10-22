package com.example.tarottales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarottales.Database.LearnDAO;
import com.example.tarottales.Model.Element;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.adapter.LearnCardAdapter;

import java.util.List;

public class LearnElementDetailActivity extends AppCompatActivity {

    //<editor-fold desc="Declare - Binding View - Action">

    // DAO
    LearnDAO learnDAO;

    // View
    TextView tvLearnElementDetailName, tvLearnElementDetailDescription, tvCardOfElement;
    RecyclerView rcvLearnElementDetailListCard;

    void bindingView() {
        tvLearnElementDetailName = findViewById(R.id.tvLearnElementDetailName);
        tvLearnElementDetailDescription = findViewById(R.id.tvLearnElementDetailDescription);
        tvCardOfElement = findViewById(R.id.tvCardOfElement);
        rcvLearnElementDetailListCard = findViewById(R.id.rcvLearnElementDetailListCard);

        if (learnDAO == null) learnDAO = new LearnDAO(this);
    }

    void bindingAction() {
        onReceiveIntent();
    }

    private void onReceiveIntent() {
        Intent intent = getIntent();
        int elementId = intent.getIntExtra("elementId", 0);
        Element element = learnDAO.getElementDetailByElementId(elementId);
        List<TarotCard> listCardOfElement = learnDAO.getListCardByElementId(elementId);
        if (elementId != 0 && element != null)
            setElementDetailToView(element, listCardOfElement);
        else
            Toast.makeText(this, "Not found Element!", Toast.LENGTH_SHORT).show();
    }

    private void setElementDetailToView(Element element, List<TarotCard> listCard) {
        // set element info
        tvLearnElementDetailName.setText(element.getName());
        tvLearnElementDetailDescription.setText(element.getDescription());
        tvCardOfElement.setText("Các lá bài thuộc nguyên tố " + element.getName());

        // set list card
        if(listCard != null){
            LearnCardAdapter learnCardAdapter = new LearnCardAdapter(listCard);
            rcvLearnElementDetailListCard.setAdapter(learnCardAdapter);
            rcvLearnElementDetailListCard.setLayoutManager(new GridLayoutManager(this, 3));
        }
    }

    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_element_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}