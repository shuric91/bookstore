package com.example.bookstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.bookstore.domain.model.BookItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainBookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainBookListFragment extends BookListFragment {

    private View mAddButton;
    private ActivityResultLauncher<Intent> editBookActivityResultLauncher;

    public MainBookListFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MainBookListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editBookActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            BookItem addedBook =
                                    (BookItem) data.getExtras().get(EditBookActivity.EXTRAS_ADDED_BOOK);
                            mBookViewModel.addNewBook(addedBook);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // This method will initialize recyclerView
        View v = super.onCreateView(inflater, container, savedInstanceState);

        //TODO - implement search filter here
        mBookViewModel.getAllBooks().observe(getViewLifecycleOwner(), (books) -> {
            mAdapter.setData(books);
            mAdapter.notifyDataSetChanged();
        });

        mAddButton = v.findViewById(R.id.fab);
        mAddButton.setOnClickListener(v1 -> onAddBookButtonClick(v1));

        return v;
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    public void onAddBookButtonClick(View view) {
        Intent intent = new Intent(this.getContext(), EditBookActivity.class);
        editBookActivityResultLauncher.launch(intent);
    }

}