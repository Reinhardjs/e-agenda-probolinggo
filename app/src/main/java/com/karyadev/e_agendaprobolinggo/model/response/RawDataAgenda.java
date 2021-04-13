package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

public class RawDataAgenda {

    @SerializedName("status_kehadiran")
    private String statusKehadiran;

    @SerializedName("perlengkapan")
    private String perlengkapan;

    @SerializedName("surat")
    private String surat;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("tanggalend")
    private String tanggalend;

    @SerializedName("sambutan")
    private String sambutan;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("id_sub_role")
    private String idSubRole;

    @SerializedName("jam")
    private String jam;

    @SerializedName("id_role")
    private String idRole;

    @SerializedName("id")
    private String id;

    @SerializedName("tata_ruangan")
    private String tataRuangan;

    @SerializedName("id_kategori")
    private String idKategori;

    @SerializedName("urutan_acara")
    private String urutanAcara;

    @SerializedName("penyelenggara")
    private String penyelenggara;

    @SerializedName("catatan")
    private String catatan;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("jamend")
    private String jamend;

    @SerializedName("petugas_protokol")
    private String petugasProtokol;

    @SerializedName("tempat")
    private String tempat;

    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @SerializedName("undangan")
    private String undangan;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("perwakilan")
    private String perwakilan;

    @SerializedName("peran_pimpinan")
    private String peranPimpinan;

    @SerializedName("pakaian")
    private String pakaian;

    @SerializedName("status")
    private String status;

    public String getStatusKehadiran() {
        return statusKehadiran;
    }

    public String getPerlengkapan() {
        return perlengkapan;
    }

    public String getSurat() {
        return surat;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTanggalend() {
        return tanggalend;
    }

    public String getSambutan() {
        return sambutan;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getIdSubRole() {
        return idSubRole;
    }

    public String getJam() {
        return jam;
    }

    public String getIdRole() {
        return idRole;
    }

    public String getId() {
        return id;
    }

    public String getTataRuangan() {
        return tataRuangan;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getUrutanAcara() {
        return urutanAcara;
    }

    public String getPenyelenggara() {
        return penyelenggara;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getJamend() {
        return jamend;
    }

    public String getPetugasProtokol() {
        return petugasProtokol;
    }

    public String getTempat() {
        return tempat;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public String getUndangan() {
        return undangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getPerwakilan() {
        return perwakilan;
    }

    public String getPeranPimpinan() {
        return peranPimpinan;
    }

    public String getPakaian() {
        return pakaian;
    }

    public String getStatus() {
        return status;
    }
}