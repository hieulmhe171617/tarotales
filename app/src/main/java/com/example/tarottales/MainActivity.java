package com.example.tarottales;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarottales.Database.DBContext;
import com.example.tarottales.fragment.ChatFragment;
import com.example.tarottales.fragment.DailyFragment;
import com.example.tarottales.fragment.LearnFragment;
import com.example.tarottales.fragment.TopicFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private DBContext dbContext;

    // Fragment
    TopicFragment topicFragment;
    DailyFragment dailyFragment;
    ChatFragment chatFragment;
    LearnFragment learnFragment;

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
        initDatabase();
        bindingView();
        bindingAction();
    }


    private void bindingAction() {
        bottomNavigationView.setOnItemSelectedListener(this::onClickSelectedNavigation);
    }

    private boolean onClickSelectedNavigation(MenuItem item) {
        if (item.getItemId() == R.id.main) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, dailyFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.topic) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, topicFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.learn) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, learnFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.chat) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, chatFragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void bindingView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        if (chatFragment == null)
            chatFragment = new ChatFragment();
        if (dailyFragment == null)
            dailyFragment = new DailyFragment();
        if (learnFragment == null)
            learnFragment = new LearnFragment();
        if (topicFragment == null)
            topicFragment = new TopicFragment();

    }

    private void initDatabase() {
        dbContext = new DBContext(this);
        new Thread(() -> {
            dbContext.getWritableDatabase();
        }).start();
    }
}