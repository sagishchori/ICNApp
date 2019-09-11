package com.sagishchori.icnapp.api.responses;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.sagishchori.icnapp.models.Value;

import java.io.Serializable;

public class JokeResponse implements Serializable {

	public int id;
	private String type;
	private Value value;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setValue(Value value){
		this.value = value;
	}

	public Value getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"JokeResponse{" + 
			"type = '" + type + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}
