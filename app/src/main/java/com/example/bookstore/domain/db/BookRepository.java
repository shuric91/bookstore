package com.example.bookstore.domain.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bookstore.domain.model.BookItem;

import java.util.List;

public class BookRepository {

    private BookDAO mBookDAO;
    private LiveData<List<BookItem>> mAllBooks;
    private LiveData<List<BookItem>> mBooksToRead;
    private LiveData<List<BookItem>> mFavoriteBooks;

    public BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        mBookDAO = db.bookDao();
        mAllBooks = mBookDAO.getAllBooks();
        mBooksToRead = mBookDAO.getBooksToRead();;
        mFavoriteBooks = mBookDAO.getFavoriteBooks();;
    }

    public LiveData<BookItem> getBookItemById(int id) {
        return mBookDAO.getBookById(id);
    }

    public void insert(BookItem bookItem) {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            mBookDAO.insertBooks(bookItem);
        });
    }

    public void update(BookItem bookItem) {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            mBookDAO.updateBooks(bookItem);
        });
    }

    public LiveData<List<BookItem>> getAllBooks() {
        return mAllBooks;
    }

    public LiveData<List<BookItem>> getBooksToRead() {
        return mBooksToRead;
    }

    public LiveData<List<BookItem>> getFavoriteBooks() {
        return mFavoriteBooks;
    }
}
