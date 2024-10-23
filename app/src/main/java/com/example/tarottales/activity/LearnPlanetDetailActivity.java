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
import com.example.tarottales.Model.Planet;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.adapter.LearnCardAdapter;
import com.example.tarottales.adapter.LearnPlanetAdapter;

import java.util.List;

public class LearnPlanetDetailActivity extends AppCompatActivity {

    //<editor-fold desc="Declare - Binding View - Action">

    // DAO
    LearnDAO learnDAO;

    // View
    TextView tvLearnPlanetDetailName, tvLearnPlanetDetailDescription, tvCardOfPlanet;
    RecyclerView rcvLearnPlanetDetailListCard;

    void bindingView() {
        tvLearnPlanetDetailName = findViewById(R.id.tvLearnPlanetDetailName);
        tvLearnPlanetDetailDescription = findViewById(R.id.tvLearnPlanetDetailDescription);
        tvCardOfPlanet = findViewById(R.id.tvCardOfPlanet);
        rcvLearnPlanetDetailListCard = findViewById(R.id.rcvLearnPlanetDetailListCard);

        if (learnDAO == null) learnDAO = new LearnDAO(this);
    }

    void bindingAction() {
        onReceivedIntent();
    }

    private void onReceivedIntent() {
        Intent intent = getIntent();
        int planetId = intent.getIntExtra("planetId", 0);
        Planet planet = learnDAO.getPlanetDetailByPlanetId(planetId);
        List<TarotCard> listCard = learnDAO.getListCardByPlanetId(planetId);
        if (planetId != 0 && planet != null)
            setPlanetDetailToView(planet, listCard);
        else
            Toast.makeText(this, "Not found Planet!", Toast.LENGTH_SHORT).show();

    }

    private void setPlanetDetailToView(Planet planet, List<TarotCard> listCard) {
        // set planet info
        tvLearnPlanetDetailName.setText(planet.getName());
        tvLearnPlanetDetailDescription.setText(planet.getDescription());
        tvCardOfPlanet.setText("Các lá bài thuộc nguyên tố " + planet.getName());

        // set list card
        if (listCard != null) {
            LearnCardAdapter learnCardAdapter = new LearnCardAdapter(listCard);
            rcvLearnPlanetDetailListCard.setAdapter(learnCardAdapter);
            rcvLearnPlanetDetailListCard.setLayoutManager(new GridLayoutManager(this, 3));
        }

    }

    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_planet_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}