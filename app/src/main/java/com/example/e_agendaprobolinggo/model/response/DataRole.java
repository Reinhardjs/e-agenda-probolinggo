package com.example.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class DataRole {

	@SerializedName("tingkatan")
	private String tingkatan;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("status")
	private String status;

	public String getTingkatan(){
		return tingkatan;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getId(){
		return id;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public String getStatus(){
		return status;
	}
}