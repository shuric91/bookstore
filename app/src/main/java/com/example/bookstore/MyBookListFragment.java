package com.example.bookstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.bookstore.domain.model.BookItem;
import com.example.bookstore.model.SearchCriteria;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBookListFragment extends BookListFragment {

    private static final String ARG_PARAM1 = "param1";

    public MyBookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BookListFragment.
     */
    public static MyBookListFragment newInstance(String param1) {
        MyBookListFragment fragment = new MyBookListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // This method will initialize recyclerView
        View v = super.onCreateView(inflater, container, savedInstanceState);

        //TODO - implement search filter here
        if (SearchCriteria.FilterCategory.TO_READ.name().equals(mParam1)) {
            mBookViewModel.getBooksToRead().observe(getViewLifecycleOwner(), this::updateBookList);
        } else if (SearchCriteria.FilterCategory.FAVORITE.name().equals(mParam1)) {
            mBookViewModel.getFavoriteBooks().observe(getViewLifecycleOwner(), this::updateBookList);
        }

        return v;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_my_book_list, container, false);
    }

    private void updateBookList(List<BookItem> books) {
        mAdapter.setData(books);
        mAdapter.notifyDataSetChanged();
    }
}