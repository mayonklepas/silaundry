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
public class Data_sms_entity {
    String id,nohp,nama_member,alamat_member,pesan;

    public Data_sms_entity(String id, String nohp, String nama_member, String alamat_member, String pesan) {
        this.id = id;
        this.nohp = nohp;
        this.nama_member = nama_member;
        this.alamat_member = alamat_member;
        this.pesan = pesan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
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

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
    
    
    
}
