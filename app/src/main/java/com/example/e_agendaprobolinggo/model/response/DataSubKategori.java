package com.example.e_agendaprobolinggo.model.response;

//import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

//@Generated("com.robohorse.robopojogenerator")
public class DataSubKategori {

	@SerializedName("id_sub_role")
	private String idSubRole;

	@SerializedName("id_role2")
	private String idRole2;

	@SerializedName("sub_role")
	private String subRole;

	@SerializedName("sub_role_img")
	private String subRoleImg;

	public void setIdSubRole(String idSubRole){
		this.idSubRole = idSubRole;
	}

	public String getIdSubRole(){
		return idSubRole;
	}

	public void setIdRole2(String idRole2){
		this.idRole2 = idRole2;
	}

	public String getIdRole2(){
		return idRole2;
	}

	public void setSubRole(String subRole){
		this.subRole = subRole;
	}

	public String getSubRole(){
		return subRole;
	}

	public void setSubRoleImg(String subRoleImg){
		this.subRoleImg = subRoleImg;
	}

	public String getSubRoleImg(){
		return subRoleImg;
	}

	@Override
 	public String toString(){
		return 
			"MasterSubRoleItem{" + 
			"id_sub_role = '" + idSubRole + '\'' + 
			",id_role2 = '" + idRole2 + '\'' + 
			",sub_role = '" + subRole + '\'' + 
			",sub_role_img = '" + subRoleImg + '\'' + 
			"}";
		}
}