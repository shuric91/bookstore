package com.example.bookstore.model;

public class SearchCriteria {

    public enum FilterCategory {
        ALL,
        TO_READ,
        FAVORITE
    }

    private FilterCategory mCategory;
    private String mSearchText;

    public void SearchCriteria(FilterCategory filterCategory, String searchText) {
        this.mCategory = filterCategory;
        this.mSearchText = searchText;
    }
}
