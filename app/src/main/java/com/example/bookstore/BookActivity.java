package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookstore.domain.model.BookItem;
import com.example.bookstore.model.BookViewModel;
import com.example.bookstore.utils.ImageUtil;

public class BookActivity extends AppCompatActivity {

    public final static String EXTRA_PARAM_BOOK_ID = "com.example.recyclerview.EXTRAS.bookID";
    private BookViewModel mBookViewModel;
    private TextView mBookDescriptionView;
    private ImageView mBookCoverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().setHomeButtonEnabled(true);

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        Intent intent = getIntent();
        int bookId = intent.getIntExtra(EXTRA_PARAM_BOOK_ID, -1);

        mBookDescriptionView = findViewById(R.id.bookAnnotation);
        mBookCoverView = findViewById(R.id.bookCoverImage);

        mBookViewModel.getBookById(bookId).observe(this, this::loadBookData);
    }

    private void loadBookData(BookItem bookItem) {
        mBookDescriptionView.setText(bookItem.getDescription());
        ImageUtil.setImage(mBookCoverView, bookItem.getImageUrl());
    }
}