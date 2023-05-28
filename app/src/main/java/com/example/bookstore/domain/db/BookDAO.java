package com.example.bookstore.domain.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookstore.domain.model.BookItem;

import java.util.List;

@Dao
public interface BookDAO {

    @Query("SELECT * FROM book where id = :bookId")
    LiveData<BookItem> getBookById(int bookId);

    @Query("SELECT * FROM book")
    LiveData<List<BookItem>> getAllBooks();

    @Query("SELECT * FROM book WHERE favorite = 1")
    LiveData<List<BookItem>> getFavoriteBooks();

    @Query("SELECT * FROM book WHERE to_read = 1")
    LiveData<List<BookItem>> getBooksToRead();

    @Query("SELECT * FROM book WHERE book_name like(:name)")
    List<BookItem> findBooksByName(String name);

    @Query("SELECT * FROM book WHERE author like(:author)")
    List<BookItem> findBooksByAuthor(String author);

    @Query("SELECT * FROM book WHERE author like(:searchParam) or book_name like(:searchParam)")
    List<BookItem> findBooksByAuthorOrName(String searchParam);

    @Insert
    void insertBooks(BookItem... books);

    @Update
    void updateBooks(BookItem... books);

    @Delete
    void deleteBooks(BookItem... books);

    @Query("DELETE FROM book")
    void deleteAll();
}
