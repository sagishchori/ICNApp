package com.sagishchori.icnapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.sagishchori.icnapp.database.Converters;

import java.io.Serializable;
import java.util.List;

@Entity (tableName = "joke_value")
public class Value implements Serializable {

	@PrimaryKey
	private int id;

	@TypeConverters(Converters.class)
	@ColumnInfo
	private List<String> categories;

	@ColumnInfo
	private String joke;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategories(List<String> categories){
		this.categories = categories;
	}

	public List<String> getCategories(){
		return categories;
	}

	public void setJoke(String joke){
		this.joke = joke;
	}

	public String getJoke(){
		return joke;
	}

	@Override
 	public String toString(){
		return 
			"Value{" + 
			"id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",joke = '" + joke + '\'' + 
			"}";
		}
}