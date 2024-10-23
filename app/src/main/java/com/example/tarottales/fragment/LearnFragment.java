package com.example.tarottales.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarottales.Database.LearnDAO;
import com.example.tarottales.Model.Element;
import com.example.tarottales.Model.Planet;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.Model.Zodiac;
import com.example.tarottales.R;
import com.example.tarottales.adapter.LearnCardAdapter;
import com.example.tarottales.adapter.LearnElementAdapter;
import com.example.tarottales.adapter.LearnPlanetAdapter;
import com.example.tarottales.adapter.LearnZodiacAdapter;
import com.example.tarottales.enumdata.EnumData;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;


public class LearnFragment extends Fragment {

    private static final int TAB_CARD = 0;
    private static final int TAB_ELEMENT = 1;
    private static final int TAB_PLANET = 2;
    private static final int TAB_ZODIAC = 3;

    public LearnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
        onCreateTabLayoutLearn();
    }

    // on create tab layout - tab selection
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
                    SetLearnCardToRecyclerView(listCard);
                    linear_action_filter.setVisibility(View.VISIBLE);
                } else if (tabPosition == TAB_ELEMENT) {
                    // Element tab
                    SetLearnElementToRecyclerView();
                    linear_action_filter.setVisibility(View.INVISIBLE);
                } else if (tabPosition == TAB_PLANET) {
                    // Planet tab
                    SetLearnPlanetToRecyclerView();
                    linear_action_filter.setVisibility(View.INVISIBLE);
                } else if (tabPosition == TAB_ZODIAC) {
                    // Zodiac tab
                    SetLearnZodiacToRecyclerView();
                    linear_action_filter.setVisibility(View.INVISIBLE);
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

    //<editor-fold desc="Declare - Binding View - Action">

    // Status popup is open
    static boolean isPopupShowing = false;
    private PopupWindow filterPopupWindow;

    // Data
    LearnDAO learnDAO;
    List<TarotCard> listCard;
    List<Element> listElement;
    List<Planet> listPlanet;
    List<Zodiac> listZodiac;

    // Tab layout
    TabLayout tab_layout_learn;
    RecyclerView rcvListCard;

    // Filter
    ImageButton imgBtnFilterCard;
    TextView tvNumberOfCard;
    LinearLayout linear_action_filter;

    // binding
    void bindingView() {
        tab_layout_learn = getView().findViewById(R.id.tab_layout_learn);
        rcvListCard = getView().findViewById(R.id.rcvListCard);
        imgBtnFilterCard = getView().findViewById(R.id.imgBtnFilterCard);
        tvNumberOfCard = getView().findViewById(R.id.tvNumberOfCard);
        linear_action_filter = getView().findViewById(R.id.linear_action_filter);

        if (learnDAO == null)
            learnDAO = new LearnDAO(getContext());
    }

    void bindingAction() {
        this.listCard = learnDAO.getListCard();
        SetLearnCardToRecyclerView(listCard);
        imgBtnFilterCard.setOnClickListener(this::onImgBtnFilterCardClick);

    }

    //</editor-fold>

    //<editor-fold desc="Binding View of popup Filter">

    // STATUS Check box
    // element
    private boolean isElementAirChecked = false;
    private boolean isElementEarthChecked = false;
    private boolean isElementFireChecked = false;
    private boolean isElementWaterChecked = false;
    // planet
    private boolean isPlanetMercuryChecked = false;
    private boolean isPlanetVenusChecked = false;
    private boolean isPlanetEarthChecked = false;
    private boolean isPlanetMarsChecked = false;
    private boolean isPlanetJupiterChecked = false;
    private boolean isPlanetSaturnChecked = false;
    private boolean isPlanetSunChecked = false;
    private boolean isPlanetMoonChecked = false;
    // zodiac
    private boolean isZodiacAriesChecked = false;
    private boolean isZodiacTaurusChecked = false;
    private boolean isZodiacGeminiChecked = false;
    private boolean isZodiacCancerChecked = false;
    private boolean isZodiacLeoChecked = false;
    private boolean isZodiacVirgoChecked = false;
    private boolean isZodiacLibraChecked = false;
    private boolean isZodiacScorpioChecked = false;
    private boolean isZodiacSagittariusChecked = false;
    private boolean isZodiacCapricornChecked = false;
    private boolean isZodiacAquariusChecked = false;
    private boolean isZodiacPiscesChecked = false;

    // CHECKBOX
    // element
    CheckBox cbElementAir, cbElementEarth, cbElementFire, cbElementWater;
    // planet
    CheckBox cbPlanetMercury, cbPlanetVenus, cbPlanetEarth, cbPlanetMars, cbPlanetJupiter, cbPlanetSaturn, cbPlanetSun, cbPlanetMoon;
    // zodiac
    CheckBox cbZodiacAries, cbZodiacTaurus, cbZodiacGemini, cbZodiacCancer, cbZodiacLeo, cbZodiacVirgo, cbZodiacLibra, cbZodiacScorpio, cbZodiacSagittarius, cbZodiacCapricorn, cbZodiacAquarius, cbZodiacPisces;
    // Button
    Button btnApplyFilter, btnFilterDefault;

    // binding view filter check box
    void bindingViewFilter(View rootView) {
        // checkbox element
        cbElementAir = rootView.findViewById(R.id.cb_element_air);
        cbElementEarth = rootView.findViewById(R.id.cb_element_earth);
        cbElementFire = rootView.findViewById(R.id.cb_element_fire);
        cbElementWater = rootView.findViewById(R.id.cb_element_water);
        // checkbox planet
        cbPlanetMercury = rootView.findViewById(R.id.cb_planet_mercury);
        cbPlanetVenus = rootView.findViewById(R.id.cb_planet_venus);
        cbPlanetEarth = rootView.findViewById(R.id.cb_planet_earth);
        cbPlanetMars = rootView.findViewById(R.id.cb_planet_mars);
        cbPlanetJupiter = rootView.findViewById(R.id.cb_planet_jupiter);
        cbPlanetSaturn = rootView.findViewById(R.id.cb_planet_saturn);
        cbPlanetSun = rootView.findViewById(R.id.cb_planet_sun);
        cbPlanetMoon = rootView.findViewById(R.id.cb_planet_moon);
        // checkbox zodiac
        cbZodiacAries = rootView.findViewById(R.id.cb_zodiac_aries);
        cbZodiacTaurus = rootView.findViewById(R.id.cb_zodiac_taurus);
        cbZodiacGemini = rootView.findViewById(R.id.cb_zodiac_gemini);
        cbZodiacCancer = rootView.findViewById(R.id.cb_zodiac_cancer);
        cbZodiacLeo = rootView.findViewById(R.id.cb_zodiac_leo);
        cbZodiacVirgo = rootView.findViewById(R.id.cb_zodiac_virgo);
        cbZodiacLibra = rootView.findViewById(R.id.cb_zodiac_libra);
        cbZodiacScorpio = rootView.findViewById(R.id.cb_zodiac_scorpio);
        cbZodiacSagittarius = rootView.findViewById(R.id.cb_zodiac_sagittarius);
        cbZodiacCapricorn = rootView.findViewById(R.id.cb_zodiac_capricorn);
        cbZodiacAquarius = rootView.findViewById(R.id.cb_zodiac_aquarius);
        cbZodiacPisces = rootView.findViewById(R.id.cb_zodiac_pisces);

        // apply button
        btnApplyFilter = rootView.findViewById(R.id.btnApplyFilter);
        btnFilterDefault = rootView.findViewById(R.id.btnFilterDefault);
    }

    // set checked foreach checkbox
    void setCheckBoxStatus() {
        cbElementAir.setChecked(isElementAirChecked);
        cbElementEarth.setChecked(isElementEarthChecked);
        cbElementFire.setChecked(isElementFireChecked);
        cbElementWater.setChecked(isElementWaterChecked);

        cbPlanetMercury.setChecked(isPlanetMercuryChecked);
        cbPlanetVenus.setChecked(isPlanetVenusChecked);
        cbPlanetEarth.setChecked(isPlanetEarthChecked);
        cbPlanetMars.setChecked(isPlanetMarsChecked);
        cbPlanetJupiter.setChecked(isPlanetJupiterChecked);
        cbPlanetSaturn.setChecked(isPlanetSaturnChecked);
        cbPlanetSun.setChecked(isPlanetSunChecked);
        cbPlanetMoon.setChecked(isPlanetMoonChecked);

        cbZodiacAries.setChecked(isZodiacAriesChecked);
        cbZodiacTaurus.setChecked(isZodiacTaurusChecked);
        cbZodiacGemini.setChecked(isZodiacGeminiChecked);
        cbZodiacCancer.setChecked(isZodiacCancerChecked);
        cbZodiacLeo.setChecked(isZodiacLeoChecked);
        cbZodiacVirgo.setChecked(isZodiacVirgoChecked);
        cbZodiacLibra.setChecked(isZodiacLibraChecked);
        cbZodiacScorpio.setChecked(isZodiacScorpioChecked);
        cbZodiacSagittarius.setChecked(isZodiacSagittariusChecked);
        cbZodiacCapricorn.setChecked(isZodiacCapricornChecked);
        cbZodiacAquarius.setChecked(isZodiacAquariusChecked);
        cbZodiacPisces.setChecked(isZodiacPiscesChecked);
    }

    //</editor-fold>

    //<editor-fold desc="Set data to recycler view method">

    // set card
    private void SetLearnCardToRecyclerView(List<TarotCard> listCard) {
        this.listCard = listCard;
        LearnCardAdapter learnCardAdapter = new LearnCardAdapter(this.listCard);
        rcvListCard.setAdapter(learnCardAdapter);
        rcvListCard.setLayoutManager(new GridLayoutManager(getContext(), 3));
        tvNumberOfCard.setText(this.listCard.size() + " lá bài");
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

    //<editor-fold desc="Binding Action">

    // filter popup show-hide
    private void onImgBtnFilterCardClick(View view) {
        isPopupShowing = !isPopupShowing;
        if (isPopupShowing) {
            @SuppressLint("InflateParams") View popupView = LayoutInflater.from(getContext()).inflate(R.layout.learn_popup_filter_card, null);
            bindingViewFilter(popupView);
            setCheckBoxStatus();
            filterPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            filterPopupWindow.showAsDropDown(view);
            btnFilterDefault.setOnClickListener(this::onBtnFilterDefaultClick);
            btnApplyFilter.setOnClickListener(this::onBtnApplyFilterClick);
        } else {
            filterPopupWindow.dismiss();
        }
    }

    private void onBtnFilterDefaultClick(View view) {
        isPopupShowing = false;

        listCard = learnDAO.getListCard();
        SetLearnCardToRecyclerView(listCard);
        // uncheck all checkbox
        isElementAirChecked = false;
        isElementEarthChecked = false;
        isElementFireChecked = false;
        isElementWaterChecked = false;

        isPlanetMercuryChecked = false;
        isPlanetVenusChecked = false;
        isPlanetEarthChecked = false;
        isPlanetMarsChecked = false;
        isPlanetJupiterChecked = false;
        isPlanetSaturnChecked = false;
        isPlanetSunChecked = false;
        isPlanetMoonChecked = false;

        isZodiacAriesChecked = false;
        isZodiacTaurusChecked = false;
        isZodiacGeminiChecked = false;
        isZodiacCancerChecked = false;
        isZodiacLeoChecked = false;
        isZodiacVirgoChecked = false;
        isZodiacLibraChecked = false;
        isZodiacScorpioChecked = false;
        isZodiacSagittariusChecked = false;
        isZodiacCapricornChecked = false;
        isZodiacAquariusChecked = false;
        isZodiacPiscesChecked = false;

        filterPopupWindow.dismiss();

    }

    private void onBtnApplyFilterClick(View view) {
        isPopupShowing = false;

        String element, planet, zodiac;
        // check - element
        StringBuilder selectedElement = new StringBuilder();
        if (cbElementAir.isChecked())
            selectedElement.append(",'").append(cbElementAir.getText().toString()).append("'");
        isElementAirChecked = cbElementAir.isChecked();
        if (cbElementEarth.isChecked())
            selectedElement.append(",'").append(cbElementEarth.getText().toString()).append("'");
        isElementEarthChecked = cbElementEarth.isChecked();
        if (cbElementFire.isChecked())
            selectedElement.append(",'").append(cbElementFire.getText().toString()).append("'");
        isElementFireChecked = cbElementFire.isChecked();
        if (cbElementWater.isChecked())
            selectedElement.append(",'").append(cbElementWater.getText().toString()).append("'");
        isElementWaterChecked = cbElementWater.isChecked();

        element = selectedElement.length() > 0 ? selectedElement.toString().substring(1) : "";

        // check - planet
        StringBuilder selectedPlanet = new StringBuilder();
        if (cbPlanetMercury.isChecked())
            selectedPlanet.append(",'").append(cbPlanetMercury.getText().toString()).append("'");
        isPlanetMercuryChecked = cbPlanetMercury.isChecked();
        if (cbPlanetVenus.isChecked())
            selectedPlanet.append(",'").append(cbPlanetVenus.getText().toString()).append("'");
        isPlanetVenusChecked = cbPlanetVenus.isChecked();
        if (cbPlanetEarth.isChecked())
            selectedPlanet.append(",'").append(cbPlanetEarth.getText().toString()).append("'");
        isPlanetEarthChecked = cbPlanetEarth.isChecked();
        if (cbPlanetMars.isChecked())
            selectedPlanet.append(",'").append(cbPlanetMars.getText().toString()).append("'");
        isPlanetMarsChecked = cbPlanetMars.isChecked();
        if (cbPlanetJupiter.isChecked())
            selectedPlanet.append(",'").append(cbPlanetJupiter.getText().toString()).append("'");
        isPlanetJupiterChecked = cbPlanetJupiter.isChecked();
        if (cbPlanetSaturn.isChecked())
            selectedPlanet.append(",'").append(cbPlanetSaturn.getText().toString()).append("'");
        isPlanetSaturnChecked = cbPlanetSaturn.isChecked();
        if (cbPlanetSun.isChecked())
            selectedPlanet.append(",'").append(cbPlanetSun.getText().toString()).append("'");
        isPlanetSunChecked = cbPlanetSun.isChecked();
        if (cbPlanetMoon.isChecked())
            selectedPlanet.append(",'").append(cbPlanetMoon.getText().toString()).append("'");
        isPlanetMoonChecked = cbPlanetMoon.isChecked();

        planet = selectedPlanet.length() > 0 ? selectedPlanet.toString().substring(1) : "";

        // check - zodiac
        StringBuilder selectedZodiac = new StringBuilder();
        if (cbZodiacAries.isChecked())
            selectedZodiac.append(",'").append(cbZodiacAries.getText().toString()).append("'");
        isZodiacAriesChecked = cbZodiacAries.isChecked();
        if (cbZodiacTaurus.isChecked())
            selectedZodiac.append(",'").append(cbZodiacTaurus.getText().toString()).append("'");
        isZodiacTaurusChecked = cbZodiacTaurus.isChecked();
        if (cbZodiacGemini.isChecked())
            selectedZodiac.append(",'").append(cbZodiacGemini.getText().toString()).append("'");
        isZodiacGeminiChecked = cbZodiacGemini.isChecked();
        if (cbZodiacCancer.isChecked())
            selectedZodiac.append(",'").append(cbZodiacCancer.getText().toString()).append("'");
        isZodiacCancerChecked = cbZodiacCancer.isChecked();
        if (cbZodiacLeo.isChecked())
            selectedZodiac.append(",'").append(cbZodiacLeo.getText().toString()).append("'");
        isZodiacLeoChecked = cbZodiacLeo.isChecked();
        if (cbZodiacVirgo.isChecked())
            selectedZodiac.append(",'").append(cbZodiacVirgo.getText().toString()).append("'");
        isZodiacVirgoChecked = cbZodiacVirgo.isChecked();
        if (cbZodiacLibra.isChecked())
            selectedZodiac.append(",'").append(cbZodiacLibra.getText().toString()).append("'");
        isZodiacLibraChecked = cbZodiacLibra.isChecked();
        if (cbZodiacScorpio.isChecked())
            selectedZodiac.append(",'").append(cbZodiacScorpio.getText().toString()).append("'");
        isZodiacScorpioChecked = cbZodiacScorpio.isChecked();
        if (cbZodiacSagittarius.isChecked())
            selectedZodiac.append(",'").append(cbZodiacSagittarius.getText().toString()).append("'");
        isZodiacSagittariusChecked = cbZodiacSagittarius.isChecked();
        if (cbZodiacCapricorn.isChecked())
            selectedZodiac.append(",'").append(cbZodiacCapricorn.getText().toString()).append("'");
        isZodiacCapricornChecked = cbZodiacCapricorn.isChecked();
        if (cbZodiacAquarius.isChecked())
            selectedZodiac.append(",'").append(cbZodiacAquarius.getText().toString()).append("'");
        isZodiacAquariusChecked = cbZodiacAquarius.isChecked();
        if (cbZodiacPisces.isChecked())
            selectedZodiac.append(",'").append(cbZodiacPisces.getText().toString()).append("'");
        isZodiacPiscesChecked = cbZodiacPisces.isChecked();

        zodiac = selectedZodiac.length() > 0 ? selectedZodiac.toString().substring(1) : "";

        // set up filter list to adapter
        this.listCard = learnDAO.getListCardByFilter(element, planet, zodiac);
        SetLearnCardToRecyclerView(this.listCard);
        filterPopupWindow.dismiss();
    }


    //</editor-fold>


}