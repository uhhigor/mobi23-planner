package com.example.mobi23_planner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UserSettingAdapter extends FragmentStateAdapter {

    public UserSettingAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChangeEmail();
            case 1:
                return new ChangePassword();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
