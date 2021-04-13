package com.karyadev.e_agendaprobolinggo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataDetailAgenda {

    @SerializedName("status_kehadiran")
    private String statusKehadiran;

    @SerializedName("id_sub_agenda")
    private String idSubAgenda;

    @SerializedName("perlengkapan")
    private String perlengkapan;

    @SerializedName("surat")
    private String surat;

    @SerializedName("status_color")
    private String statusColor;

    @SerializedName("btn_download_surat")
    private int btnDownloadSurat;

    @SerializedName("btn_download_sambutan")
    private int btnDownloadSambutan;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("tanggalend")
    private String tanggalend;

    @SerializedName("tampilkan_komentar")
    private int tampilkanKomentar;

    @SerializedName("sambutan")
    private String sambutan;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("jam")
    private String jam;

    @SerializedName("id")
    private String id;

    @SerializedName("tata_ruangan")
    private String tataRuangan;

    @SerializedName("sub_agenda")
    private String subAgenda;

    @SerializedName("id_kategori")
    private String idKategori;

    @SerializedName("urutan_acara")
    private String urutanAcara;

    @SerializedName("id_agenda")
    private String idAgenda;

    @SerializedName("penyelenggara")
    private String penyelenggara;

    @SerializedName("catatan")
    private String catatan;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("btn_tambah_komentar")
    private int btnTambahKomentar;

    @SerializedName("created_by")
    private String createdBy;

    @SerializedName("agenda")
    private String agenda;

    @SerializedName("jamend")
    private String jamend;

    @SerializedName("petugas_protokol")
    private String petugasProtokol;

    @SerializedName("status_agenda")
    private String statusAgenda;

    @SerializedName("tempat")
    private String tempat;

    @SerializedName("status_box")
    private String statusBox;

    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @SerializedName("list_komentar")
    private List<ListKomentarAgenda> listKomentar;

    @SerializedName("id_encode")
    private String idEncode;

    @SerializedName("undangan")
    private String undangan;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("detail")
    private String detail;

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

    public String getIdSubAgenda() {
        return idSubAgenda;
    }

    public String getPerlengkapan() {
        return perlengkapan;
    }

    public String getSurat() {
        return surat;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public int getBtnDownloadSurat() {
        return btnDownloadSurat;
    }

    public int getBtnDownloadSambutan() {
        return btnDownloadSambutan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTanggalend() {
        return tanggalend;
    }

    public int getTampilkanKomentar() {
        return tampilkanKomentar;
    }

    public String getSambutan() {
        return sambutan;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getJam() {
        return jam;
    }

    public String getId() {
        return id;
    }

    public String getTataRuangan() {
        return tataRuangan;
    }

    public String getSubAgenda() {
        return subAgenda;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getUrutanAcara() {
        return urutanAcara;
    }

    public String getIdAgenda() {
        return idAgenda;
    }

    public String getPenyelenggara() {
        return penyelenggara;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getKategori() {
        return kategori;
    }

    public int getBtnTambahKomentar() {
        return btnTambahKomentar;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAgenda() {
        return agenda;
    }

    public String getJamend() {
        return jamend;
    }

    public String getPetugasProtokol() {
        return petugasProtokol;
    }

    public String getStatusAgenda() {
        return statusAgenda;
    }

    public String getTempat() {
        return tempat;
    }

    public String getStatusBox() {
        return statusBox;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public List<ListKomentarAgenda> getListKomentar() {
        return listKomentar;
    }

    public String getIdEncode() {
        return idEncode;
    }

    public String getUndangan() {
        return undangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getDetail() {
        return detail;
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