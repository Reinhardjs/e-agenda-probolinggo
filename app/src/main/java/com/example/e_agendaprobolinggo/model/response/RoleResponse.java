package com.example.e_agendaprobolinggo.model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RoleResponse {

	@SerializedName("data")
	private List<DataRole> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public List<DataRole> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}
}