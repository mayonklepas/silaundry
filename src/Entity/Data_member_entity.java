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
public class Data_member_entity {

    String id_member;
    String nama_member;
    String alamat_member;
    String nohp_member;
    String tanggal_daftar;

    public Data_member_entity(String id_member, String nama_member, String alamat_member, String nohp_member, String tanggal_daftar) {
        this.id_member = id_member;
        this.nama_member = nama_member;
        this.alamat_member = alamat_member;
        this.nohp_member = nohp_member;
        this.tanggal_daftar = tanggal_daftar;
    }

    public Data_member_entity() {
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

    public String getAlamat_member() {
        return alamat_member;
    }

    public void setAlamat_member(String alamat_member) {
        this.alamat_member = alamat_member;
    }

    public String getNohp_member() {
        return nohp_member;
    }

    public void setNohp_member(String nohp_member) {
        this.nohp_member = nohp_member;
    }

    public String getTanggal_daftar() {
        return tanggal_daftar;
    }

    public void setTanggal_daftar(String tanggal_daftar) {
        this.tanggal_daftar = tanggal_daftar;
    }
}
