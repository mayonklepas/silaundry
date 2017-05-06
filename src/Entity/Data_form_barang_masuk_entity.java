/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Mulyadi
 */
public class Data_form_barang_masuk_entity {
    String idbarangmasuk,layanan,jumlah,keterangan,biaya;

    public Data_form_barang_masuk_entity(String idbarangmasuk,String layanan, String jumlah, String keterangan, String biaya) {
        this.idbarangmasuk=idbarangmasuk;
        this.layanan = layanan;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.biaya = biaya;
    }

    public String getIdbarangmasuk() {
        return idbarangmasuk;
    }

    public void setIdbarangmasuk(String idbarangmasuk) {
        this.idbarangmasuk = idbarangmasuk;
    }
    
    

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }
    
    
    
    
}
