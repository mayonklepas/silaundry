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
public class Data_biaya_entity {
    
    String id_biaya,kategori_biaya,nama_biaya;

    public Data_biaya_entity(String id_biaya, String kategori_biaya, String nama_biaya) {
        this.id_biaya = id_biaya;
        this.kategori_biaya = kategori_biaya;
        this.nama_biaya = nama_biaya;
    }

    public Data_biaya_entity() {
    }

    public String getId_biaya() {
        return id_biaya;
    }

    public void setId_biaya(String id_biaya) {
        this.id_biaya = id_biaya;
    }

    public String getKategori_biaya() {
        return kategori_biaya;
    }

    public void setKategori_biaya(String kategori_biaya) {
        this.kategori_biaya = kategori_biaya;
    }

    public String getNama_biaya() {
        return nama_biaya;
    }

    public void setNama_biaya(String nama_biaya) {
        this.nama_biaya = nama_biaya;
    }
    
    
    
}
