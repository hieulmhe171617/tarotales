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
import com.example.tarottales.Model.Zodiac;
import com.example.tarottales.R;
import com.example.tarottales.adapter.LearnCardAdapter;

import java.util.List;

public class LearnZodiacDetailActivity extends AppCompatActivity {

    //<editor-fold desc="Declare - Binding View - Action">

    // DAO
    LearnDAO learnDAO;

    // View
    TextView tvLearnZodiacDetailName, tvLearnZodiacDetailDescription, tvCardOfZodiac;
    RecyclerView rcvLearnZodiacDetailListCard;

    void bindingView() {
        tvLearnZodiacDetailName = findViewById(R.id.tvLearnZodiacDetailName);
        tvLearnZodiacDetailDescription = findViewById(R.id.tvLearnZodiacDetailDescription);
        tvCardOfZodiac = findViewById(R.id.tvCardOfZodiac);
        rcvLearnZodiacDetailListCard = findViewById(R.id.rcvLearnZodiacDetailListCard);

        if (learnDAO == null) learnDAO = new LearnDAO(this);
    }

    void bindingAction(){
        onReceiveIntent();
    }

    private void onReceiveIntent() {
        Intent intent = getIntent();
        int zodiacId = intent.getIntExtra("zodiacId", 0);
        Zodiac zodiac = learnDAO.getZodiacDetailByZodiacId(zodiacId);
        List<TarotCard> listCardOfElement = learnDAO.getListCardByZodiacId(zodiacId);
        if (zodiacId != 0 && zodiac != null)
            setZodiacDetailToView(zodiac, listCardOfElement);
        else
            Toast.makeText(this, "Not found Element!", Toast.LENGTH_SHORT).show();
    }

    private void setZodiacDetailToView(Zodiac zodiac, List<TarotCard> listCard){
        // set element info
        tvLearnZodiacDetailName.setText(zodiac.getName());
        tvLearnZodiacDetailDescription.setText(zodiac.getDescription());
        tvCardOfZodiac.setText("Các lá bài thuộc nguyên tố " + zodiac.getName());

        // set list card
        if(listCard != null){
            LearnCardAdapter learnCardAdapter = new LearnCardAdapter(listCard);
            rcvLearnZodiacDetailListCard.setAdapter(learnCardAdapter);
            rcvLearnZodiacDetailListCard.setLayoutManager(new GridLayoutManager(this, 3));
        }
    }

    //</editor-fold>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_zodiac_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}