/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Sessionuser;
import static controller.Form_barang_masukController.id_barang;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.swing.JOptionPane;
import model.Model;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Mulyadi
 */
public class Socketserverhandle extends Task<Void> {

    ServerSocket ss;
    Socket s;
    Model m = new Model();
    Filecontrol fc = new Filecontrol();
    Sessionuser ses = new Sessionuser();

    public Connection conn() throws SQLException {
        m.conn = DriverManager.getConnection(m.host, m.user, m.password);
        return m.conn;
    }

    @Override
    protected Void call() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            ss = new ServerSocket(555);
            while (true) {
                s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                printstruk(dis.readUTF());
                //JOptionPane.showMessageDialog(null, "push dari android");
            }
        } catch (IOException ex) {
            Logger.getLogger(Socketserverhandle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void printstruk(String data) {
        String spdata[] = data.split(":");
        for (int i = 0; i < 2; i++) {
            try {
                HashMap hash = new HashMap();
                hash.put("perusahaan", fc.namaperusahaan());
                hash.put("alamat", fc.alamat());
                hash.put("nohp", fc.nohp());
                hash.put("keterangan", fc.namapemilik());
                hash.put("user", spdata[1]);
                hash.put("id_barang", spdata[0]);
                hash.put("uangmuka", 0);//Double.parseDouble(spdata[2].replaceAll("[,.]", "")));
                JasperReport jr = (JasperReport) JRLoader.loadObject(new File("laporan/Struck.jasper"));
                JasperPrint jp = JasperFillManager.fillReport(jr, hash, conn());
                JasperPrintManager.printReport(jp, false);
            } catch (JRException ex) {
                Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                //err.showerror(ex);
            } catch (SQLException ex) {
                Logger.getLogger(Form_barang_masukController.class.getName()).log(Level.SEVERE, null, ex);
                //err.showerror(ex);
            }
        }

    }

}
