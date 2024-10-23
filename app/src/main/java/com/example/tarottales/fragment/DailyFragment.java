package com.example.tarottales.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tarottales.Database.DBContext;
import com.example.tarottales.Database.TarotCardDAO;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.service.ResetOpenDaily;

import java.util.Calendar;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DailyFragment extends Fragment {
    private CardView cvDay;
    private CardView cvMonth;
    private ImageView ivTarotCard;
    private ImageView ivBlur;
    private TextView tvInfo1;
    private TextView tvInfo2;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private DBContext dbContext;
    private MonthlyFragment monthlyFragment;
    //shared preferences
    private boolean isOpenToday;
    //
    private Button btnResetAll;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
        setupAlarmService();
    }


    private void onClickCVDay(View view) {

    }

    private void onClickCVMonth(View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment_container, monthlyFragment)
                .commit();
    }

    private void onClickTarotCard(View view) {
        if (!isOpenToday) {
            new Thread(() -> {
                TarotCard card = ((TarotCardDAO) dbContext).getRandomTarotCardForDailyDay();
                if (card != null) {
                    if (getActivity() != null) {
                        editor.putInt("cardId", card.getId());
                        editor.putBoolean("isOpenToday", true);
                        editor.commit();
                        isOpenToday = true;
                        getActivity().runOnUiThread(() ->{
                                flipTarotCard(card);
                        });
                    }
                }
            }).start();

        } else {
            //mo sang intent chi tiet card
        }
    }

    private void flipTarotCard(TarotCard card) {
        AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_out);
        AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
        setRightOut.setTarget(ivTarotCard);
        setRightOut.start();
        ivTarotCard.setImageResource(card.getImage());
        setLeftIn.setTarget(ivTarotCard);
        setLeftIn.start();
        //
        tvInfo1.setText(card.getName());
        tvInfo2.setText(card.getOtherName());
        setBlurForBackground(card.getImage());
    }


    private void bindingAction() {
        cvDay.setOnClickListener(this::onClickCVDay);
        cvMonth.setOnClickListener(this::onClickCVMonth);
        ivTarotCard.setOnClickListener(this::onClickTarotCard);
        checkOpenDailyDay();
        //---------------------------------------------------
        btnResetAll.setOnClickListener(this::onClickBtnResetAll);

    }

    private void onClickBtnResetAll(View view) {
        SharedPreferences pref = getActivity().getSharedPreferences("tarotDailyDay", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        //
        SharedPreferences pref2 = getActivity().getSharedPreferences("tarotMonthly", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = pref2.edit();
        editor2.clear();
        editor2.commit();
    }


    private void bindingView() {
        cvDay = getView().findViewById(R.id.cvDay);
        cvMonth = getView().findViewById(R.id.cvMonth);
        ivTarotCard = getView().findViewById(R.id.ivTarotCard);
        tvInfo1 = getView().findViewById(R.id.tvInfo1);
        tvInfo2 = getView().findViewById(R.id.tvInfo2);
        ivBlur = getView().findViewById(R.id.ivBlur);
        //fragment
        if (monthlyFragment == null) {
            monthlyFragment = new MonthlyFragment();
        }
        //share preferend
        pref = getActivity().getSharedPreferences("tarotDailyDay", MODE_PRIVATE);
        editor = pref.edit();
        dbContext = new TarotCardDAO(getContext());
        //--------------------------------
        btnResetAll = getView().findViewById(R.id.btnResetAll);
        //-------------------------------
    }

    private void checkOpenDailyDay() {
        isOpenToday = pref.getBoolean("isOpenToday", false);
        if (isOpenToday) {
            //da mo roi
            new Thread(()->{
                TarotCard card = ((TarotCardDAO) dbContext).getTarotCardById(pref.getInt("cardId", 0));
                if(card != null){
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() ->{
                            ivTarotCard.setImageResource(card.getImage());
                            tvInfo1.setText(card.getName());
                            tvInfo2.setText(card.getOtherName());
                            setBlurForBackground(card.getImage());
                        });
                    }
                }
            }).start();
        } else {
            //chua mo
            ivTarotCard.setImageResource(R.drawable.tarot_back);
            setBlurForBackground(R.drawable.tarot_back);
        }
    }

    private void setBlurForBackground(int imgSrc) {
        // lam mo background dung glide
        Glide.with(this)
                .asBitmap()
                .load(imgSrc)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(50)))
                .into(ivBlur);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupAlarmService() {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), ResetOpenDaily.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            PendingIntent pending = PendingIntent.getBroadcast(getContext(), 10, intent, PendingIntent.FLAG_IMMUTABLE);
            //thong bao se duoc reset vao 6h sang moi ngay

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                //da qua 6h sang
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }
}