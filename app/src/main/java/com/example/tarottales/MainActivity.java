package com.example.tarottales;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        bottomNavigationView.setOnItemSelectedListener(this::onClickSelectedNavigation);
    }

    private boolean onClickSelectedNavigation(MenuItem item) {
        if (item.getItemId() == R.id.main) {
            //
            return true;
        }
        return false;
    }

    private void bindingView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }
}