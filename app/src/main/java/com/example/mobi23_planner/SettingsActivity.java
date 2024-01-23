package com.example.mobi23_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobi23_planner.data.DataManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new SettingsAdapter(getSupportFragmentManager(), getLifecycle()));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("User Settings");
                            break;
                        case 1:
                            tab.setText("App Settings");
                            break;
                    }
                }
        ).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logo) {
            startActivity(new Intent(SettingsActivity.this, TasksListActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            DataManager.resetInstance();
            startActivity(new Intent(SettingsActivity.this, MainPageActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}

