package com.example.tarottales.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarottales.Database.LearnDAO;
import com.example.tarottales.Database.TarotCardDAO;
import com.example.tarottales.Model.Element;
import com.example.tarottales.Model.Planet;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.Model.Zodiac;
import com.example.tarottales.R;
import com.example.tarottales.adapter.LearnCardAdapter;
import com.example.tarottales.adapter.LearnElementAdapter;
import com.example.tarottales.adapter.LearnPlanetAdapter;
import com.example.tarottales.adapter.LearnZodiacAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class LearnFragment extends Fragment {

    private static final int TAB_CARD = 0;
    private static final int TAB_ELEMENT = 1;
    private static final int TAB_PLANET = 2;
    private static final int TAB_ZODIAC = 3;

    //<editor-fold desc="Declare - Binding View - Action">

    // Data
    LearnDAO learnDAO;
    List<TarotCard> listCard;
    List<Element> listElement;
    List<Planet> listPlanet;
    List<Zodiac> listZodiac;

    // Tab layout
    TabLayout tab_layout_learn;
    RecyclerView rcvListCard;

    // binding
    void bindingView() {
        tab_layout_learn = getView().findViewById(R.id.tab_layout_learn);
        rcvListCard = getView().findViewById(R.id.rcvListCard);
        if(learnDAO == null)
            learnDAO = new LearnDAO(getContext());
    }

    void bindingAction() {
        SetLearnCardToRecyclerView();
    }

    //</editor-fold>

    public LearnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }


    // on create tab layout
    private void onCreateTabLayoutLearn() {
        // create tabs
        tab_layout_learn.addTab(tab_layout_learn.newTab().setText("Bộ bài")); // 0
        tab_layout_learn.addTab(tab_layout_learn.newTab().setText("Nguyên tố")); // 1
        tab_layout_learn.addTab(tab_layout_learn.newTab().setText("Hành tinh")); // 2
        tab_layout_learn.addTab(tab_layout_learn.newTab().setText("Hoàng đạo")); // 3
        // set default selected tab
        Objects.requireNonNull(tab_layout_learn.getTabAt(0)).select();
        // handle on select
        tab_layout_learn.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // on tab selected
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                if (tabPosition == TAB_CARD) {
                    // Card tab
                    SetLearnCardToRecyclerView();
                } else if (tabPosition == TAB_ELEMENT) {
                    // Element tab
                    SetLearnElementToRecyclerView();
                } else if (tabPosition == TAB_PLANET) {
                    // Planet tab
                    SetLearnPlanetToRecyclerView();
                } else if (tabPosition == TAB_ZODIAC) {
                    // Zodiac tab
                    SetLearnZodiacToRecyclerView();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
        onCreateTabLayoutLearn();
    }

    //<editor-fold desc="Set data to recycler view method">

    // set card
    private void SetLearnCardToRecyclerView() {
        this.listCard = learnDAO.getListCard();
        LearnCardAdapter learnCardAdapter = new LearnCardAdapter(this.listCard);
        rcvListCard.setAdapter(learnCardAdapter);
        rcvListCard.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    // set element
    private void SetLearnElementToRecyclerView() {
        this.listElement = learnDAO.getListElement();
        LearnElementAdapter learnElementAdapter = new LearnElementAdapter(this.listElement);
        rcvListCard.setAdapter(learnElementAdapter);
        rcvListCard.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    // set planet
    private void SetLearnPlanetToRecyclerView() {
        this.listPlanet = learnDAO.getListPlanet();
        LearnPlanetAdapter learnPlanetAdapter = new LearnPlanetAdapter(this.listPlanet);
        rcvListCard.setAdapter(learnPlanetAdapter);
        rcvListCard.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    // set zodiac
    private void SetLearnZodiacToRecyclerView() {
        this.listZodiac = learnDAO.getListZodiac();
        LearnZodiacAdapter learnZodiacAdapter = new LearnZodiacAdapter(this.listZodiac);
        rcvListCard.setAdapter(learnZodiacAdapter);
        rcvListCard.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    //</editor-fold>




}