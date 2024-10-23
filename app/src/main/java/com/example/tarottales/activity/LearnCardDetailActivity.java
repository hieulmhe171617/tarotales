package com.example.tarottales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarottales.Database.LearnDAO;
import com.example.tarottales.Model.Element;
import com.example.tarottales.Model.Planet;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.Model.Zodiac;
import com.example.tarottales.R;

public class LearnCardDetailActivity extends AppCompatActivity {

    //<editor-fold desc="Declare - Binding View - Action">

    // DAO
    LearnDAO learnDAO;

    // View
    ImageView imgLearnCardDetail;
    TextView tvLearnCardDetailTitle, tvLearnCardDetailCardNumber,
            tvLearnCardDetailName, tvLearnCardDetailElement, tvLearnCardDetailPlanet,
            tvLearnCardDetailZodiac, tvLearnCardDetailKeyword,
            tvLearnCardDetailOverview, tvLearnCardDetailJob,
            tvLearnCardDetailLove, tvLearnCardDetailFinance, tvLearnCardDetailHealth,
            tvLearnCardDetailSpirit;

    void bindingView() {
        imgLearnCardDetail = findViewById(R.id.imgLearnCardDetail);
        tvLearnCardDetailTitle = findViewById(R.id.tvLearnCardDetailTitle);
        tvLearnCardDetailCardNumber = findViewById(R.id.tvLearnCardDetailCardNumber);
        tvLearnCardDetailName = findViewById(R.id.tvLearnCardDetailName);
        tvLearnCardDetailElement = findViewById(R.id.tvLearnCardDetailElement);
        tvLearnCardDetailPlanet = findViewById(R.id.tvLearnCardDetailPlanet);
        tvLearnCardDetailZodiac = findViewById(R.id.tvLearnCardDetailZodiac);
        tvLearnCardDetailKeyword = findViewById(R.id.tvLearnCardDetailKeyword);
        tvLearnCardDetailOverview = findViewById(R.id.tvLearnCardDetailOverview);
        tvLearnCardDetailJob = findViewById(R.id.tvLearnCardDetailJob);
        tvLearnCardDetailLove = findViewById(R.id.tvLearnCardDetailLove);
        tvLearnCardDetailFinance = findViewById(R.id.tvLearnCardDetailFinance);
        tvLearnCardDetailHealth = findViewById(R.id.tvLearnCardDetailHealth);
        tvLearnCardDetailSpirit = findViewById(R.id.tvLearnCardDetailSpirit);
        if (learnDAO == null) learnDAO = new LearnDAO(this);
    }

    void bindingAction() {
        onReceiveIntent();
    }

    // receive intent
    private void onReceiveIntent() {
        Intent rIntetn = getIntent();
        TarotCard cardDetail = (TarotCard) learnDAO.getCardDetailByCardId(rIntetn.getIntExtra("cardId", 0));
        if (cardDetail != null)
            setCardDetailToView(cardDetail);
        else Toast.makeText(this, "Invalid Card!", Toast.LENGTH_SHORT).show();
    }

    //</editor-fold>

    //<editor-fold desc="Static Method">

    void setCardDetailToView(TarotCard cartDetail) {
        // set detail info
        imgLearnCardDetail.setImageResource(cartDetail.getImage());
        tvLearnCardDetailTitle.setText(cartDetail.getName());
        tvLearnCardDetailCardNumber.setText("Lá số #" + cartDetail.getCardNumber());
        tvLearnCardDetailName.setText(cartDetail.getOtherName());
        tvLearnCardDetailKeyword.setText(cartDetail.getKeyword());
        tvLearnCardDetailOverview.setText(cartDetail.getOverview());
        tvLearnCardDetailJob.setText(cartDetail.getJob());
        tvLearnCardDetailLove.setText(cartDetail.getLove());
        tvLearnCardDetailFinance.setText(cartDetail.getFinance());
        tvLearnCardDetailHealth.setText(cartDetail.getHealth());
        tvLearnCardDetailSpirit.setText(cartDetail.getSpirit());

        // set reference name
        StringBuilder refName = new StringBuilder();
        // element
        for (Element e : cartDetail.getElements()) {
            refName.append(",").append(e.getName()).append(" ");
        }
        tvLearnCardDetailElement.setText(refName.toString().substring(1));
        refName.setLength(0);
        // planet
        for (Planet e : cartDetail.getPlanets()) {
            refName.append(",").append(e.getName()).append(" ");
        }
        tvLearnCardDetailPlanet.setText(refName.toString().substring(1));
        refName.setLength(0);
        // zodiac
        for (Zodiac e : cartDetail.getZodiacs()) {
            refName.append(",").append(e.getName()).append(" ");
        }
        tvLearnCardDetailZodiac.setText(refName.toString().substring(1));
    }

    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_card_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_learn_element_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}