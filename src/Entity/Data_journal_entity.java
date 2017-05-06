/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Minami
 */
public class Data_journal_entity {
    String id_journal,tanggal_journal,id_biaya,nama_biaya,rincian,jumlah;

    public Data_journal_entity(String id_journal, String tanggal_journal, String id_biaya, String nama_biaya, String rincian, String jumlah) {
        this.id_journal = id_journal;
        this.tanggal_journal = tanggal_journal;
        this.id_biaya = id_biaya;
        this.nama_biaya = nama_biaya;
        this.rincian = rincian;
        this.jumlah = jumlah;
    }

    public String getId_journal() {
        return id_journal;
    }

    public void setId_journal(String id_journal) {
        this.id_journal = id_journal;
    }

    public String getTanggal_journal() {
        return tanggal_journal;
    }

    public void setTanggal_journal(String tanggal_journal) {
        this.tanggal_journal = tanggal_journal;
    }

    public String getId_biaya() {
        return id_biaya;
    }

    public void setId_biaya(String id_biaya) {
        this.id_biaya = id_biaya;
    }

    public String getNama_biaya() {
        return nama_biaya;
    }

    public void setNama_biaya(String nama_biaya) {
        this.nama_biaya = nama_biaya;
    }

    public String getRincian() {
        return rincian;
    }

    public void setRincian(String rincian) {
        this.rincian = rincian;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
    
    
    
    
}
