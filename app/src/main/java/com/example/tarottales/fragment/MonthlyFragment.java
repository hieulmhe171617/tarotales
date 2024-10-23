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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tarottales.Database.DBContext;
import com.example.tarottales.Database.TarotCardDAO;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.R;
import com.example.tarottales.service.ResetMonthly;

import java.util.Calendar;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MonthlyFragment extends Fragment {
    private ImageView ivTarotCard1, ivTarotCard2, ivTarotCard3, ivTarotCard4;
    private ImageView ivBlur;
    private CardView cvDay;
    private TextView tvTitle;
    private DailyFragment dailyFragment;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private DBContext dbContext;
    //shared preferences
    private boolean isWeek1Open;
    private boolean isWeek2Open;
    private boolean isWeek3Open;
    private boolean isWeek4Open;
    private int week1;
    private int week2;
    private int week3;
    private int week4;
    private List<TarotCard> cards;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
        setupMonthlyService();
    }


    private void bindingAction() {
        cvDay.setOnClickListener(this::onClickCVDay);
        ivTarotCard1.setOnClickListener(this::onClickTarotCard);
        ivTarotCard2.setOnClickListener(this::onClickTarotCard);
        ivTarotCard3.setOnClickListener(this::onClickTarotCard);
        ivTarotCard4.setOnClickListener(this::onClickTarotCard);
    }

    private void onClickTarotCard(View view) {
        boolean isWeekOpen;
        int weekTmp;
        String putWeekOpen;
        if (view.getId() == R.id.ivTarotCard1) {
            isWeekOpen = isWeek1Open;
            weekTmp = week1;
            putWeekOpen = "isWeek1Open";
        } else if (view.getId() == R.id.ivTarotCard2) {
            isWeekOpen = isWeek2Open;
            weekTmp = week2;
            putWeekOpen = "isWeek2Open";
        } else if (view.getId() == R.id.ivTarotCard3) {
            isWeekOpen = isWeek3Open;
            weekTmp = week3;
            putWeekOpen = "isWeek3Open";
        } else if (view.getId() == R.id.ivTarotCard4) {
            isWeekOpen = isWeek4Open;
            weekTmp = week4;
            putWeekOpen = "isWeek4Open";
        } else {
            weekTmp = 0;
            isWeekOpen = false;
            putWeekOpen = null;
        }
        //
        if (!isWeekOpen) {
            //mo ra
            new Thread(() -> {
                TarotCard card = ((TarotCardDAO) dbContext).getTarotCardById(weekTmp);
                editor.putBoolean(putWeekOpen, true);
                editor.commit();
                setIsWeekOpenVariable(putWeekOpen);
                if (card != null) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            flipTarotCard(card, (ImageView) view);
                        });
                    }
                }
            }).start();
        } else {
            //sang intent chi tiet
        }
    }

    private void setIsWeekOpenVariable(String putWeekOpen) {
        switch (putWeekOpen) {
            case "isWeek1Open":
                isWeek1Open = true;
                break;
            case "isWeek2Open":
                isWeek2Open = true;
                break;
            case "isWeek3Open":
                isWeek3Open = true;
                break;
            case "isWeek4Open":
                isWeek4Open = true;
                break;
        }
    }

    private void onClickCVDay(View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment_container, dailyFragment)
                .commit();
    }

    private void bindingView() {
        ivTarotCard1 = getView().findViewById(R.id.ivTarotCard1);
        ivTarotCard2 = getView().findViewById(R.id.ivTarotCard2);
        ivTarotCard3 = getView().findViewById(R.id.ivTarotCard3);
        ivTarotCard4 = getView().findViewById(R.id.ivTarotCard4);
        ivBlur = getView().findViewById(R.id.ivBlur);
        cvDay = getView().findViewById(R.id.cvDay);
        tvTitle = getView().findViewById(R.id.tvTitle);
        //
        dbContext = new TarotCardDAO(getContext());
        //shared
        pref = getActivity().getSharedPreferences("tarotMonthly", MODE_PRIVATE);
        editor = pref.edit();
        //
        if (dailyFragment == null) {
            dailyFragment = new DailyFragment();
        }
        setTitle();
        setBlurForBackground();
        checkOpenMonthly();
    }

    private void setBlurForBackground() {
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.addition_picture_1)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(50)))
                .into(ivBlur);
    }

    private void setTitle() {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        tvTitle.setText("Tháng " + month + " của bạn");
    }

    private void flipTarotCard(TarotCard card, ImageView imageView) {
        AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_out);
        AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
        setRightOut.setTarget(imageView);
        setRightOut.start();
        imageView.setImageResource(card.getImage());
        setLeftIn.setTarget(imageView);
        setLeftIn.start();
    }

    private void checkOpenMonthly() {
        week1 = pref.getInt("week1", 0);
        week2 = pref.getInt("week2", 0);
        week3 = pref.getInt("week3", 0);
        week4 = pref.getInt("week4", 0);
        //
        isWeek1Open = pref.getBoolean("isWeek1Open", false);
        isWeek2Open = pref.getBoolean("isWeek2Open", false);
        isWeek3Open = pref.getBoolean("isWeek3Open", false);
        isWeek4Open = pref.getBoolean("isWeek4Open", false);
        //
        if (week1 == 0) {
            new Thread(() -> {
                cards = ((TarotCardDAO) dbContext).getTarotCardListRandom(4);
                if (cards != null && cards.size() == 4) {
                    week1 = cards.get(0).getId();
                    week2 = cards.get(1).getId();
                    week3 = cards.get(2).getId();
                    week4 = cards.get(3).getId();
                    //
                    editor.putInt("week1", week1);
                    editor.putInt("week2", week2);
                    editor.putInt("week3", week3);
                    editor.putInt("week4", week4);
                    editor.commit();
                }
            }).start();
        }
        //
        updateTarotCardUI(isWeek1Open, week1, ivTarotCard1);
        updateTarotCardUI(isWeek2Open, week2, ivTarotCard2);
        updateTarotCardUI(isWeek3Open, week3, ivTarotCard3);
        updateTarotCardUI(isWeek4Open, week4, ivTarotCard4);
    }

    private void updateTarotCardUI(boolean isWeekOpen, int cardId, ImageView imageView) {
        if (isWeekOpen) {
            new Thread(() -> {
                TarotCard card = ((TarotCardDAO) dbContext).getTarotCardById(cardId);
                if (card != null && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        imageView.setImageResource(card.getImage());
                    });
                }
            }).start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupMonthlyService() {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), ResetMonthly.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            PendingIntent pending = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            //4 la' se duoc reset moi thang
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                //da qua 6h sang
                calendar.add(Calendar.MONTH, 1);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pending);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }
}