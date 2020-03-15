package com.example.e_agendaprobolinggo.model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AgendaResponse{

	@SerializedName("data")
	private List<DataAgenda> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public void setData(List<DataAgenda> data){
		this.data = data;
	}

	public List<DataAgenda> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AgendaResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}