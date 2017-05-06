/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_member_entity;
import Entity.Sessionuser;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Form_journalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tjumlah;
    @FXML
    private ComboBox cbiaya;
    @FXML
    private TextArea trincian;
    @FXML
    private Button bsimpan, bclear;
    @FXML
    private DatePicker tdate;
    public static String id_journal, id_biaya, rincian, tanggal,jumlah;

    Model m = new Model();
    Data_member_entity e = new Data_member_entity();
    Sessionuser ses = new Sessionuser();
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        simpan();
        bersihkan();
        cbiaya.getEditor().setText(id_biaya);
        trincian.setText(rincian);
        tjumlah.setText(jumlah);
        LocalDate date = LocalDate.parse(tanggal);
        tdate.setValue(date);
        loadcombo();
        tdate.requestFocus();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-clear-3.png").toString()));
        bclear.setTooltip(new Tooltip("Bersihka Field"));

    }

    public void rawsimpan() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String[] idbiaya = cbiaya.getEditor().getText().split(":");
            m.connect();
            if (id_journal.equals("")) {
                Object[] o = new Object[5];
                o[0] = tdate.getValue().toString();
                o[1] = idbiaya[0];
                o[2] = trincian.getText();
                o[3] = Double.parseDouble(tjumlah.getText().replaceAll("[.,]", ""));
                o[4] = ses.getId_akun();
                m.insert("INSERT INTO data_journal(tanggal,id_biaya,rincian_journal,jumlah,id_akun)"
                        + "VALUES(?::date,?,?,?,?)", 5, o);

            } else {
                Object[] o = new Object[6];
                o[0] = tdate.getValue().toString();
                o[1] = idbiaya[0];
                o[2] = trincian.getText();
                o[3] = Double.parseDouble(tjumlah.getText().replaceAll("[.,]", ""));
                o[4] = ses.getId_akun();
                o[5] = Integer.parseInt(id_journal);
                m.insert("UPDATE data_journal SET "
                        + "tanggal=?::date,id_biaya=?,"
                        + "rincian_journal=?,"
                        + "jumlah=?,"
                        + "id_akun=? WHERE id_journal=?", 6, o);
            }
            m.disconnect();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Pemberitahuan");
            al.setHeaderText("Data Berhasil Disimpan");
            al.setContentText("Refresh Data jika perubahan belum terlihat");
            al.showAndWait();
            tdate.requestFocus();
        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(Form_journalController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void simpan() {
        bsimpan.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                rawsimpan();
            }
        });

        trincian.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (event.getCode().equals(KeyCode.ENTER)) {
                    rawsimpan();
                }
            }
        });

    }

    public void loadcombo() {
        cbiaya.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                    Object[] o = new Object[3];
                    o[0] = "%" + cbiaya.getEditor().getText() + "%";
                    o[1] = "%" + cbiaya.getEditor().getText() + "%";
                    o[2] = "%" + cbiaya.getEditor().getText() + "%";
                    m.connect();
                    ResultSet res = m.readdetail("SELECT id_biaya,nama_biaya "
                            + "FROM data_biaya WHERE id_biaya::text ILIKE ? OR nama_biaya ILIKE ? OR "
                            + "kategori_biaya ILIKE ?"
                            + "", 3, o).executeQuery();
                    ObservableList ols = FXCollections.observableArrayList();
                    while (res.next()) {
                        ols.add(res.getString("id_biaya") + ":" + res.getString("nama_biaya"));
                    }
                    cbiaya.setItems(ols);
                    m.disconnect();
                    cbiaya.show();
                } catch (SQLException ex) {
                    Logger.getLogger(Form_journalController.class.getName()).log(Level.SEVERE, null, ex);
                   err.showerror(ex);
                }

            }
        });
    }

    public void bersihkan() {
        bclear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tdate.setValue(LocalDate.now());
                cbiaya.getEditor().setText("");
                trincian.setText("");
                tjumlah.setText("");
            }
        });
    }
}
