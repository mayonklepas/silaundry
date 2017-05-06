/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Sessionuser;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

/**
 * FXML Controller class
 *
 * @author minami
 */
public class FormbayarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tjumlahuang;
    @FXML
    private Label ltotal, lkembalian,luangmuka,lsisa;
    @FXML
    private Button bsimpan;
    public static double total,uangmuka;
    public static String id_barang;
    NumberFormat nf = NumberFormat.getInstance();
    Model m = new Model();
    Sessionuser ses = new Sessionuser();
    Errorhandler err = new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        load();
        bayar();
        simpantransaksi();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data Transaksi"));

    }

    private void load() {
        ltotal.setText("Rp " + nf.format(total));
        luangmuka.setText("Rp "+nf.format(uangmuka));
        lsisa.setText("Rp "+nf.format(total-uangmuka));
    }

    private void bayar() {
        tjumlahuang.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {

                    double jumlahuang = 0;
                    if (tjumlahuang.getText().isEmpty()) {
                        jumlahuang = 0;
                    } else {
                        jumlahuang = Double.parseDouble(tjumlahuang.getText().replaceAll("[qwertyuiopasdfghjkl;'zxcvbnm,.!@#$%^&*()_+]", ""));
                    }
                    String hasil = nf.format(jumlahuang - (total-uangmuka));
                    lkembalian.setText(hasil);
                } catch (Exception ex) {
                    err.showerror(ex);
                }

            }

        });
    }

    private void rawsimpan() {
        try {
            m.connect();
            Object[] o1 = new Object[3];
            o1[0] = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            o1[1] = ses.getId_akun();
            o1[2] = id_barang;
            m.insert("INSERT INTO data_journal(id_biaya,tanggal,rincian_journal,jumlah,id_akun) "
                    + "SELECT '001',?::date,CONCAT(nama_layanan,',',round(jumlah::numeric,2),' KG'),tarif_layanan * jumlah,? "
                    + "FROM data_barang_masuk INNER JOIN data_layanan "
                    + "ON data_barang_masuk.id_layanan=data_layanan.id_layanan WHERE id_barang=? ", 3, o1);
            Object[] o2 = new Object[3];
            o2[0] = new Setnumber().nourut("TR", "id_barang", "data_barang_masuk");
            o2[1] = ses.getId_akun();
            o2[2] = id_barang;
            //m.insert("INSERT INTO transaksi(id_transaksi,id_akun,id_proses) values(?,?,?)", 3, o2);
            //m.delete("DELETE FROM data_barang_selesai WHERE id_proses=?", kodeproses);
            Object[] o3 = new Object[1];
            o3[0] = id_barang;
            m.update("UPDATE data_master_barang SET status=2 WHERE id_barang=?", 1, o3);
            m.disconnect();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Pemberitahuan");
            al.setHeaderText("Proses Sukses");
            al.setContentText("Data Transaksi Telah disimpan di Journal");
            al.showAndWait();

        } catch (SQLException ex) {
            Logger.getLogger(FormbayarController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void simpantransaksi() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                rawsimpan();
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        tjumlahuang.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (event.getCode().equals(KeyCode.ENTER)) {
                    rawsimpan();
                    final Node source = (Node) event.getSource();
                    final Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }
        });

    }

}
