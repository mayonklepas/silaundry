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
public class Sessionuser {
    public static String id_akun,namauser,tipe;

    public Sessionuser(String id_akun, String namauser, String tipe) {
        this.id_akun = id_akun;
        this.namauser = namauser;
        this.tipe=tipe;
    }
    
     public Sessionuser() {
    }


    public static String getTipe() {
        return tipe;
    }

    public static void setTipe(String tipe) {
        Sessionuser.tipe = tipe;
    }

    public String getId_akun() {
        return id_akun;
    }

    public void setId_akun(String id_akun) {
        this.id_akun = id_akun;
    }

    public String getNamauser() {
        return namauser;
    }

    public void setNamauser(String namauser) {
        this.namauser = namauser;
    }
    
    
    
}
