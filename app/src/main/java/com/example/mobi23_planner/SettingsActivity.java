package com.example.mobi23_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


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
        //if logo is clicked back to main activity
        menu.findItem(R.id.logo).setOnMenuItemClickListener(item -> {
            startActivity(new Intent(SettingsActivity.this, TasksListActivity.class));
            return true;
        });
        return true;
    }

}

