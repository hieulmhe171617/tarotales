package com.example.tarottales.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarottales.R;
import com.example.tarottales.dto.TopicHistoryDTO;
import com.example.tarottales.file.JsonTopicHistoryDTOHelper;

public class DetailsOneItemInHistoryActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvCard1, tvCard2, tvCard3;
    private ImageView ivCard1, ivCard2, ivCard3;
    private TextView tvNote;
    private TopicHistoryDTO historyItem;
    private Button btnEditNote;

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
        receiveItemFromIntent();
    }

    private void receiveItemFromIntent() {
        historyItem = (TopicHistoryDTO) getIntent().getSerializableExtra("topicHistoryDTO");
        bindingDataToView();
    }

    private void bindingDataToView() {
        String note = "Ghi chú:\nTrải bài lưu tại " + historyItem.getDate() + " (" + historyItem.getTime().substring(0, 5) + ")\n" + historyItem.getNote();
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
        btnEditNote.setOnClickListener(this::onClickEditNote);
    }


    private void onClickOpenCard(View view) {
        //mo sang intent chi tiet
        Intent intent = new Intent(this, LearnCardDetailActivity.class);
        if(view.getId() == R.id.ivCard1){
            intent.putExtra("cardId",historyItem.getCard1().getId());
        } else if(view.getId() == R.id.ivCard2){
            intent.putExtra("cardId",historyItem.getCard2().getId());
        } else if(view.getId() == R.id.ivCard3){
            intent.putExtra("cardId",historyItem.getCard3().getId());
        }
        startActivity(intent);
    }

    private void onClickEditNote(View view) {
        final EditText input = new EditText(this);
        input.setMaxLines(8);
        input.setVerticalScrollBarEnabled(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ghi chú");
        builder.setMessage("Chỉnh sửa nội dung ghi chú");
        builder.setView(input);
        //ok
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String note;
                if (input.getText().toString().isEmpty()) {
                    note = "Không có ghi chú";
                } else {
                    note = input.getText().toString();
                    if (note.length() >= 5000) {
                        Toast.makeText(DetailsOneItemInHistoryActivity.this, "Vui lòng ghi chú nội dung dưới 5000 ký tự!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //luu vao json
                saveChangeHistoryToJson(note);
            }
        });
        //cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DetailsOneItemInHistoryActivity.this, "Đã hủy!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void saveChangeHistoryToJson(String note) {
        JsonTopicHistoryDTOHelper.updateNote(this, historyItem.getDate(), historyItem.getTime(), note, JsonTopicHistoryDTOHelper.FILE_NAME);
        String newNote = "Ghi chú:\nTrải bài lưu tại " + historyItem.getDate() + " (" + historyItem.getTime().substring(0, 5) + ")\n" + note;
        tvNote.setText(newNote);
        Toast.makeText(this, "Cập nhật ghi chú thành công!", Toast.LENGTH_SHORT).show();
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
        btnEditNote = findViewById(R.id.btnEditNote);
    }


}