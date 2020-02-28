package com.example.e_agendaprobolinggo.model.response;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class DataRegister {

	@SerializedName("password")
	private String password;

	@SerializedName("opd")
	private String opd;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jabatan")
	private String jabatan;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setOpd(String opd){
		this.opd = opd;
	}

	public String getOpd(){
		return opd;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJabatan(String jabatan){
		this.jabatan = jabatan;
	}

	public String getJabatan(){
		return jabatan;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"password = '" + password + '\'' + 
			",opd = '" + opd + '\'' + 
			",nama = '" + nama + '\'' + 
			",jabatan = '" + jabatan + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",created_by = '" + createdBy + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}