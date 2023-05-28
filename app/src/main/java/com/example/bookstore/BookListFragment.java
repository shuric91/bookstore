package com.example.bookstore;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.domain.adapters.BookListAdapter;
import com.example.bookstore.model.BookViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public abstract class BookListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    protected String mParam1;
    protected RecyclerView mRecyclerView;
    protected BookListAdapter mAdapter;
    protected BookViewModel mBookViewModel;

    public BookListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = getFragmentView(inflater, container);
        mRecyclerView = v.findViewById(R.id.bookListRecyclerView);
        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mAdapter = new BookListAdapter(v.getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }

    protected abstract View getFragmentView(LayoutInflater inflater, ViewGroup container);
}