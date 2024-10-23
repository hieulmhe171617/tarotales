package com.example.tarottales.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarottales.R;
import com.example.tarottales.activity.TopicDetailsActivity;
import com.example.tarottales.activity.TopicHistoryActivity;

public class TopicFragment extends Fragment {
    private TextView tvWaiting;
    private Button btnStart;
    private ImageView ivHistory;
    private Handler handler = new Handler();
    private int secondsPassed = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        btnStart.setOnClickListener(this::onClickBtnStart);
        ivHistory.setOnClickListener(this::onClickIvHistory);
    }

    private void onClickIvHistory(View view) {
        Intent intent = new Intent(getContext(), TopicHistoryActivity.class);
        startActivity(intent);
        resetToBeginStatus();
    }

    private void onClickBtnStart(View view) {
        tvWaiting.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.GONE);
        startCountdown();
    }

    private void startCountdown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                secondsPassed++;
                String dots = generateDots(secondsPassed);
                tvWaiting.setText("Tập trung năng lượng của bạn về chủ đề muốn xem trong 5s" + dots);
                if (secondsPassed < 5) {
                    handler.postDelayed(this, 1000); // Sau 1 giây gọi lại hàm này
                } else {
                    if (isAdded()) {
                        //mo sang intent moi
                        resetToBeginStatus();
                        Intent intent = new Intent(getContext(), TopicDetailsActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }, 1000);
    }

    private void resetToBeginStatus() {
        secondsPassed = 0;
        tvWaiting.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
    }

    private String generateDots(int i) {
        String dots = "";
        for (int j = 1; j <= i; j++) {
            dots += ".";
        }
        return dots;
    }


    private void bindingView() {
        tvWaiting = getView().findViewById(R.id.tvWaiting);
        btnStart = getView().findViewById(R.id.btnStart);
        ivHistory = getView().findViewById(R.id.ivHistory);
        tvWaiting.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}