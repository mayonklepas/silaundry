/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author minami
 */
public class Data_transaksi_entity {

    String id_barang, tanggal_masuk, id_member, nama_member, nama_layanan, jumlah, nohp, biaya;

    public Data_transaksi_entity(String id_barang, String tanggal_masuk, String id_member, String nama_member, String nohp, String nama_layanan, String jumlah, String biaya) {
        this.id_barang = id_barang;
        this.tanggal_masuk = tanggal_masuk;
        this.id_member = id_member;
        this.nama_member = nama_member;
        this.nohp = nohp;
        this.nama_layanan = nama_layanan;
        this.jumlah = jumlah;
        this.biaya = biaya;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getId_member() {
        return id_member;
    }

    public void setId_member(String id_member) {
        this.id_member = id_member;
    }

    public String getNama_member() {
        return nama_member;
    }

    public void setNama_member(String nama_member) {
        this.nama_member = nama_member;
    }


    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

}
