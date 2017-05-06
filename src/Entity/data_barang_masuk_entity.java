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
public class data_barang_masuk_entity {

  String id_barang,tanggal_masuk, tanggal_selesai, id_member, nama_member, nohp, uangmuka,status,alamat;

    public data_barang_masuk_entity(String id_barang,String tanggal_masuk, String tanggal_selesai, String id_member, String nama_member, String nohp, String uangmuka) {
        this.id_barang = id_barang;
        this.tanggal_masuk = tanggal_masuk;
        this.tanggal_selesai = tanggal_selesai;
        this.id_member = id_member;
        this.nama_member = nama_member;
        this.nohp = nohp;
        this.uangmuka = uangmuka;
    }
    
    public data_barang_masuk_entity(String id_barang,String tanggal_masuk, String tanggal_selesai, String id_member, String nama_member, String nohp, String uangmuka,String status) {
        this.id_barang = id_barang;
        this.tanggal_masuk = tanggal_masuk;
        this.tanggal_selesai = tanggal_selesai;
        this.id_member = id_member;
        this.nama_member = nama_member;
        this.nohp = nohp;
        this.uangmuka = uangmuka;
        this.status=status;
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

    
    
    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
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

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getUangmuka() {
        return uangmuka;
    }

    public void setUangmuka(String uangmuka) {
        this.uangmuka = uangmuka;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
  
  
    
    
    
    
    
}
