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
public class Data_layanan_entity {
    String id_layanan,nama_layanan,ukuran_layanan,tarif_layanan;

    public Data_layanan_entity(String id_layanan, String nama_layanan, String ukuran_layanan, String tarif_layanan) {
        this.id_layanan = id_layanan;
        this.nama_layanan = nama_layanan;
        this.ukuran_layanan = ukuran_layanan;
        this.tarif_layanan = tarif_layanan;
    }

    public Data_layanan_entity() {
    }
    
    

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getNama_layanan() {
        return nama_layanan;
    }

    public void setNama_layanan(String nama_layanan) {
        this.nama_layanan = nama_layanan;
    }

    public String getUkuran_layanan() {
        return ukuran_layanan;
    }

    public void setUkuran_layanan(String ukuran_layanan) {
        this.ukuran_layanan = ukuran_layanan;
    }

    public String getTarif_layanan() {
        return tarif_layanan;
    }

    public void setTarif_layanan(String tarif_layanan) {
        this.tarif_layanan = tarif_layanan;
    }
    
    
    
}
