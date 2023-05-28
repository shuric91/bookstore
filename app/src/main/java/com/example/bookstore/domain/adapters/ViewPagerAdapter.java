package com.example.bookstore.domain.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookstore.MainBookListFragment;
import com.example.bookstore.MyBookListFragment;
import com.example.bookstore.model.SearchCriteria;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return MyBookListFragment.newInstance(SearchCriteria.FilterCategory.TO_READ.name());
            case 2:
                return MyBookListFragment.newInstance(SearchCriteria.FilterCategory.FAVORITE.name());
            default:
                return MainBookListFragment.newInstance();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
