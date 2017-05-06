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
public class Data_akun_entity {
    String id_akun,nama_akun,username,password,tipe;

    public Data_akun_entity(String id_akun, String nama_akun, String username, String password,String tipe) {
        this.id_akun = id_akun;
        this.nama_akun = nama_akun;
        this.username = username;
        this.password = password;
        this.tipe=tipe;
    }

    public Data_akun_entity() {
    }

    public String getId_akun() {
        return id_akun;
    }

    public void setId_akun(String id_akun) {
        this.id_akun = id_akun;
    }

    public String getNama_akun() {
        return nama_akun;
    }

    public void setNama_akun(String nama_akun) {
        this.nama_akun = nama_akun;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
    
    
    
}
