/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_member_entity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Form_memberController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tnama, tnohp;
    @FXML
    private TextArea talamat;
    @FXML
    private Button bsimpan, bclear;
    public static String id, nama, alamat, nohp;

    Model m = new Model();
    Data_member_entity e = new Data_member_entity();
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        simpan();
        bersihkan();
        tnama.setText(nama);
        talamat.setText(alamat);
        tnohp.setText(nohp);
        tnohp.requestFocus();
        bsimpan.setGraphic(new ImageView(getClass().getResource("/image/document-save-5.png").toString()));
        bsimpan.setTooltip(new Tooltip("Simpan Data"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-clear-3.png").toString()));
        bclear.setTooltip(new Tooltip("Bersihkan Field"));
    }

    private void rawsimpan() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            m.connect();

            if (id.equals("")) {
                Object[] o = new Object[4];
                o[0] = new Setnumber().nourutv3("MB", "id_member", "data_member");
                o[1] = tnama.getText();
                o[2] = talamat.getText();
                o[3] = tnohp.getText();
                m.insert("INSERT INTO data_member(id_member,nama_member,alamat_member,nohp_member)"
                        + "VALUES(?,?,?,?)", 4, o);

            } else {
                Object[] o = new Object[4];
                o[0] = tnama.getText();
                o[1] = talamat.getText();
                o[2] = tnohp.getText();
                o[3] = id;
                m.insert("UPDATE data_member SET nama_member=?,alamat_member=?,nohp_member=? WHERE id_member=?", 4, o);
            }
            m.disconnect();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Pemberitahuan");
            al.setHeaderText("Data Berhasil Disimpan");
            al.setContentText("Refresh Data jika perubahan belum terlihat");
            al.showAndWait();
            tnama.requestFocus();

        } catch (SQLException ex) {
            Logger.getLogger(Form_memberController.class.getName()).log(Level.SEVERE, null, ex);
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

        tnohp.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                tnama.clear();
                talamat.clear();
                tnohp.clear();
            }
        });
    }
}
