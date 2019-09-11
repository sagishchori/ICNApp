package com.sagishchori.icnapp.database;

public interface DbInterface<T> {

    void onDataInsert();

    void onDataFailedToInsert();

    void onDataDeleted();

    void onDataFailedToDelete();

    void onDataFetch(T data);
}
