package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//import javax.annotation.Generated;

//@Generated("com.robohorse.robopojogenerator")
public class DataKategori {

    @SerializedName("role")
    private String role;

    @SerializedName("id_role")
    private String idRole;

    @SerializedName("role_img")
    private String roleImg;

    @SerializedName("master_sub_role")
    private List<DataSubKategori> masterSubRole;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setRoleImg(String roleImg) {
        this.roleImg = roleImg;
    }

    public String getRoleImg() {
        return roleImg;
    }

    public void setMasterSubRole(List<DataSubKategori> masterSubRole) {
        this.masterSubRole = masterSubRole;
    }

    public List<DataSubKategori> getMasterSubRole() {
        return masterSubRole;
    }

    @Override
    public String toString() {
        return
                "DataItem{" +
                        "role = '" + role + '\'' +
                        ",id_role = '" + idRole + '\'' +
                        ",role_img = '" + roleImg + '\'' +
                        ",master_sub_role = '" + masterSubRole + '\'' +
                        "}";
    }
}