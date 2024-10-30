package com.example.tarottales.activity;

import static java.security.AccessController.getContext;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tarottales.Database.DBContext;
import com.example.tarottales.Database.TarotCardDAO;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.dto.TopicHistoryDTO;
import com.example.tarottales.file.JsonTopicHistoryDTOHelper;
import com.example.tarottales.fragment.ChatFragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TopicDetailsActivity extends AppCompatActivity {
    private ImageView ivCard1, ivCard2, ivCard3;
    private TextView tvCard1, tvCard2, tvCard3;
    private Button btnSave, btnAi;
    private ImageView ivBack, ivHistory;
    //
    DBContext dbContext;
    //
    private int numberCardOpen = 0;
    private List<TarotCard> cards;
    private boolean isCard1Open = false;
    private boolean isCard2Open = false;
    private boolean isCard3Open = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_topic_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        assignTarotCard();
        bindingAction();
    }

    private void assignTarotCard() {
        new Thread(()->{
            cards = ((TarotCardDAO)dbContext).getTarotCardListRandom(3);
        }).start();
    }

    private void bindingAction() {
        ivCard1.setOnClickListener(this::onClickOpenCard);
        ivCard2.setOnClickListener(this::onClickOpenCard);
        ivCard3.setOnClickListener(this::onClickOpenCard);
        ivBack.setOnClickListener(this::onClickBack);
        ivHistory.setOnClickListener(this::onClickViewHistory);
        btnSave.setOnClickListener(this::onClickSave);
        btnAi.setOnClickListener(this::onClickAi);
    }

    private void onClickAi(View view) {

        String[] topics = {"Tình duyên","Công việc", "Sức khỏe","Tinh thần","Khác"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn muốn hỏi về chủ đề nào?");
        builder.setItems(topics, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedTopic;
                if (which == topics.length - 1) {
                    selectedTopic = "";
                } else {
                    selectedTopic = topics[which];
                }
                String question = "Ý nghĩa của bộ bài Tarot gồm 3 lá: " + cards.get(0).getName()
                        + ", " + cards.get(1).getName() + ", " + cards.get(2).getName() + " trong vấn đề "+ selectedTopic +" sắp tới là gì? " ;
                ChatFragment chatFragment = new ChatFragment();
                Bundle args = new Bundle();
                args.putString("initialText", question);
                chatFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, chatFragment)
                        .addToBackStack(null)
                        .commit();
                hiddenButon();
            }

        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        displayButon();
    }

    private void onClickViewHistory(View view) {
        //mo sang intent lich su
        Intent intent = new Intent(this, TopicHistoryActivity.class);
        startActivity(intent);
    }

    private void onClickSave(View view) {
        //luu vao json file theo dto
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateTime = currentDateTime.format(formatter);
        showNotePopup(dateTime);
    }

    private void showNotePopup(String dateTime) {
        //tao popup len de nhap note
        final EditText input = new EditText(this);
        input.setMaxLines(8);
        input.setVerticalScrollBarEnabled(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ghi chú");
        builder.setMessage("Bạn có muốn ghi chú gì vào nội dung trải bài này không?");
        builder.setView(input);
        //ok
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String note;
                if(input.getText().toString().isEmpty()){
                    note = "Không có ghi chú";
                } else {
                    note = input.getText().toString();
                    if(note.length() >= 5000){
                        Toast.makeText(TopicDetailsActivity.this, "Vui lòng ghi chú nội dung dưới 5000 ký tự!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //luu vao json
                saveHistoryToJson(dateTime, note);
                Toast.makeText(TopicDetailsActivity.this, "Lưu kết quả thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        //cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TopicDetailsActivity.this, "Đã hủy!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void saveHistoryToJson(String dateTime, String note) {
        TopicHistoryDTO dto = new TopicHistoryDTO();
        String date = dateTime.split(" ")[0];
        String time = dateTime.split(" ")[1];
        dto.setCard1(cards.get(0));
        dto.setCard2(cards.get(1));
        dto.setCard3(cards.get(2));
        dto.setDate(date);
        dto.setTime(time);
        dto.setNote(note);
        JsonTopicHistoryDTOHelper.addTopicHistoryDTO(this,dto,JsonTopicHistoryDTOHelper.FILE_NAME);
        btnSave.setEnabled(false);
    }

    private void onClickBack(View view) {
        finish();
    }

    private void onClickOpenCard(View view) {
        if(view.getId() == R.id.ivCard1){
           openCardOrViewCardDetails((ImageView) view, tvCard1, cards.get(0), isCard1Open);
           isCard1Open = true;
        } else if(view.getId() == R.id.ivCard2){
            openCardOrViewCardDetails((ImageView) view, tvCard2, cards.get(1), isCard2Open);
            isCard2Open = true;
        } else if(view.getId() == R.id.ivCard3){
            openCardOrViewCardDetails((ImageView) view, tvCard3, cards.get(2), isCard3Open);
            isCard3Open = true;
        }
    }

    private void openCardOrViewCardDetails(ImageView view, TextView tvCard, TarotCard tarotCard, boolean isCardOpen) {
        if(!isCardOpen){
            tvCard.setText(tarotCard.getName());
            flipTarotCard(tarotCard, view);
            numberCardOpen++;
            checkOpenSaveAndRestart();
        } else {

            //mo sang intent chi tiet
            Intent intent = new Intent(this, LearnCardDetailActivity.class);
            intent.putExtra("cardId",tarotCard.getId());
            startActivity(intent);
        }

    }

    private void checkOpenSaveAndRestart() {
        if(numberCardOpen == 3){
            btnSave.setVisibility(View.VISIBLE);
            btnAi.setVisibility(View.VISIBLE);
        }
    }

    private void flipTarotCard(TarotCard card, ImageView imageView) {
        AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_right_out);
        AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_left_in);
        setRightOut.setTarget(imageView);
        setRightOut.start();
        imageView.setImageResource(card.getImage());
        setLeftIn.setTarget(imageView);
        setLeftIn.start();
    }

    private void bindingView() {
        ivCard1 = findViewById(R.id.ivCard1);
        ivCard2 = findViewById(R.id.ivCard2);
        ivCard3 = findViewById(R.id.ivCard3);
        tvCard1 = findViewById(R.id.tvCard1);
        tvCard2 = findViewById(R.id.tvCard2);
        tvCard3 = findViewById(R.id.tvCard3);
        btnSave = findViewById(R.id.btnSave);
        btnAi = findViewById(R.id.btnAi);
        ivBack = findViewById(R.id.ivBack);
        ivHistory = findViewById(R.id.ivHistory);
        btnSave.setVisibility(View.GONE);
        btnAi.setVisibility(View.GONE);
        //
        dbContext = new TarotCardDAO(this);
    }
    private void hiddenButon(){
        btnAi.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
    }
    public void displayButon(){
        btnAi.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
    }

}