package com.example.bookstore.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookstore.domain.db.BookRepository;
import com.example.bookstore.domain.model.BookItem;

import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private final BookRepository mRepository;
    private final LiveData<List<BookItem>> mAllBooks;
    private final LiveData<List<BookItem>> mBooksToRead;
    private final LiveData<List<BookItem>> mFavoriteBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        
        mRepository = new BookRepository(application);
        mAllBooks = mRepository.getAllBooks();
        mBooksToRead = mRepository.getBooksToRead();
        mFavoriteBooks = mRepository.getFavoriteBooks();
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

    public LiveData<BookItem> getBookById(int id) {
        return mRepository.getBookItemById(id);
    }

    public void addBookToReadList(BookItem bookItem) {
        if (bookItem != null && !bookItem.isToRead()) {
            bookItem.setToRead(true);
            mRepository.update(bookItem);
        }
    }

    public void removeBookFromReadList(BookItem bookItem) {
        if (bookItem != null && bookItem.isToRead()) {
            bookItem.setToRead(false);
            mRepository.update(bookItem);
        }
    }

    public void addBookToFavoriteList(BookItem bookItem) {
        if (bookItem != null && !bookItem.isFavorite()) {
            bookItem.setFavorite(true);
            mRepository.update(bookItem);
        }
    }

    public void removeBookFromFavoriteList(BookItem bookItem) {
        if (bookItem != null && bookItem.isFavorite()) {
            bookItem.setFavorite(false);
            mRepository.update(bookItem);
        }
    }

    public void addNewBook(BookItem bookItem) {
        if (bookItem != null) {
            mRepository.insert(bookItem);
        }
    }
}
