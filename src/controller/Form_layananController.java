/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_layanan_entity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Form_layananController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tid, tnama, tukuran, ttarif;
    @FXML
    private Button bsimpan, bclear;
    public static String id, nama, ukuran, tarif;

    Model m = new Model();
    Data_layanan_entity e = new Data_layanan_entity();
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        simpan();
        bersihkan();
        tid.setText(id);
        tnama.setText(nama);
        tukuran.setText(ukuran);
        ttarif.setText(tarif);
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-clear-3.png").toString()));
        bclear.setTooltip(new Tooltip("Bersihka Field"));
    }

    private void rawsimpan() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            m.connect();
            if (id.equals("")) {
                Object[] o = new Object[4];
                o[0] = Integer.parseInt(tid.getText());
                o[1] = tnama.getText();
                o[2] = Double.parseDouble(tukuran.getText());
                o[3] = Double.parseDouble(ttarif.getText().replace(".", ""));
                m.insert("INSERT INTO data_layanan(id_layanan,nama_layanan,ukuran_layanan,tarif_layanan)"
                        + "VALUES(?,?,?,?)", 4, o);

            } else {
                Object[] o = new Object[5];
                o[0] = Integer.parseInt(tid.getText());
                o[1] = tnama.getText();
                o[2] = Double.parseDouble(tukuran.getText());
                o[3] = Double.parseDouble(ttarif.getText().replace(".", ""));
                o[4] = Integer.parseInt(id);
                m.insert("UPDATE data_layanan SET id_layanan=?,nama_layanan=?,"
                        + "ukuran_layanan=?,tarif_layanan=? WHERE id_layanan=?", 5, o);
            }
            m.disconnect();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Pemberitahuan");
            al.setHeaderText("Data Berhasil Disimpan");
            al.setContentText("Refresh Data jika perubahan belum terlihat");
            al.showAndWait();

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(Form_layananController.class.getName()).log(Level.SEVERE, null, ex);
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

        ttarif.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (event.getCode().equals(KeyCode.ENTER)) {
                    rawsimpan();
                }
            }
        });
    }

    public void bersihkan() {
        bclear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tid.clear();
                tnama.clear();
                tukuran.clear();
                ttarif.clear();
            }
        });
    }
}
