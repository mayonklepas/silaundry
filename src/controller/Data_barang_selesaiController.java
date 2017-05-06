/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_member_entity;
import Entity.Sessionuser;
import Entity.data_barang_masuk_entity;
import com.sun.deploy.util.FXLoader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Data_barang_selesaiController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param
     */
    @FXML
    private TableView<data_barang_masuk_entity> tbview;
    @FXML
    private TableColumn<data_barang_masuk_entity, String> id_barang, tanggal_masuk, tanggal_selesai,
            id_member, nama_member, nohp, uangmuka, status;

    @FXML
    private Button brefresh, bprev, bnext, bclear;
    @FXML
    private TextField tcari, tlimit;
    @FXML
    private Label ltotal;
    ObservableList ols = FXCollections.observableArrayList();
    Model m = new Model();
    int offset, limit;
    NumberFormat nf = NumberFormat.getInstance();
    Sessionuser ses = new Sessionuser();
    Errorhandler err = new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewdata();
        pagging();
        cari();
        clear();
        refresh();
        humancontrol();

        bnext.setGraphic(new ImageView(getClass().getResource("/image/go-next-3.png").toString()));
        bnext.setTooltip(new Tooltip("Data selanjutnya"));
        bprev.setGraphic(new ImageView(getClass().getResource("/image/go-previous-3.png").toString()));
        bprev.setTooltip(new Tooltip("Data sebelumnya"));
        brefresh.setGraphic(new ImageView(getClass().getResource("/image/view_refresh.png").toString()));
        brefresh.setTooltip(new Tooltip("Refresh Data awal"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-delete-6.png").toString()));
        bclear.setTooltip(new Tooltip("Bersihkan Field"));

    }

    private void viewdata() {
        try {
            //bprev.setDisable(true);
            bclear.setDisable(true);
            offset = 0;
            limit = Integer.parseInt(tlimit.getText());
            tbview.getItems().clear();
            tbview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            m.connect();
            ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,data_master_barang.id_member as id_member,"
                    + "nama_member,nohp_member,uangmuka,status FROM data_master_barang LEFT JOIN "
                    + "data_member ON data_master_barang.id_member = data_member.id_member WHERE status != 0 "
                    + "ORDER BY id_barang DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
            while (res.next()) {
                String konstatus = "";
                if (res.getInt("status") == 1) {
                    konstatus = "Belum Dibayar";
                } else {
                    konstatus = "Sudah Dibayar";
                }
                ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                        res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                        res.getString("id_member"), res.getString("nama_member"),
                        res.getString("nohp_member"), nf.format(res.getDouble("uangmuka")),
                        konstatus));

            }
            ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master_barang WHERE status != 0").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
            tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
            tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
            id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_barang_selesaiController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void pagging() {
        bprev.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    offset = offset - Integer.parseInt(tlimit.getText());
                    tbview.getItems().clear();
                    m.connect();
                    ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                            + "data_master_barang.id_member as id_member,"
                            + "nama_member,nohp_member,uangmuka,status FROM data_master_barang LEFT JOIN "
                            + "data_member ON data_master_barang.id_member = data_member.id_member WHERE status!=0 "
                            + "ORDER BY id_barang DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        String konstatus = "";
                        if (res.getInt("status") == 1) {
                            konstatus = "Belum Dibayar";
                        } else {
                            konstatus = "Sudah Dibayar";
                        }
                        ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                                res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                                res.getString("id_member"), res.getString("nama_member"),
                                res.getString("nohp_member"), nf.format(res.getDouble("uangmuka")),
                                konstatus));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_barang_masuk WHERE status!=0").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

                    m.disconnect();
                    id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
                    tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
                    tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
                    id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
                    uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
                    status.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tbview.setItems(ols);

                } catch (Exception ex) {
                    Logger.getLogger(Data_barang_selesaiController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }
        });

        bnext.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    offset = offset + Integer.parseInt(tlimit.getText());
                    tbview.getItems().clear();
                    m.connect();
                    ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                            + "data_master_barang.id_member as id_member,"
                            + "nama_member,nohp_member,uangmuka,status FROM data_master_barang LEFT JOIN "
                            + "data_member ON data_master_barang.id_member = data_member.id_member WHERE status!=0 "
                            + "ORDER BY id_barang DESC LIMIT " + limit + " OFFSET " + offset + " ").executeQuery();
                    while (res.next()) {
                        String konstatus = "";
                        if (res.getInt("status") == 1) {
                            konstatus = "Belum Dibayar";
                        } else {
                            konstatus = "Sudah Dibayar";
                        }
                        ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                                res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                                res.getString("id_member"), res.getString("nama_member"),
                                res.getString("nohp_member"), nf.format(res.getDouble("uangmuka")),
                                konstatus));

                    }
                    ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master_barang WHERE status != 0").executeQuery();
                    res2.next();
                    ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

                    m.disconnect();
                    id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
                    tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
                    tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
                    id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
                    nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
                    nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
                    uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
                    status.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tbview.setItems(ols);

                } catch (Exception ex) {
                    Logger.getLogger(Data_barang_selesaiController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }
        });

    }

    private void rawcari() {
        try {
            tbview.getItems().clear();
            Object[] o = new Object[6];
            o[0] = "%" + tcari.getText() + "%";
            o[1] = "%" + tcari.getText() + "%";
            o[2] = "%" + tcari.getText() + "%";
            o[3] = "%" + tcari.getText() + "%";
            o[4] = "%" + tcari.getText() + "%";
            o[5] = "%" + tcari.getText() + "%";
            m.connect();
            ResultSet res = m.readdetail("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                    + "data_master_barang.id_member as id_member,"
                    + "nama_member,nohp_member,uangmuka,status FROM data_master_barang LEFT JOIN "
                    + "data_member ON data_master_barang.id_member = data_member.id_member "
                    + "WHERE status!=0 AND id_barang ILIKE ? OR "
                    + "tanggal_masuk::text ILIKE ? OR "
                    + "tanggal_selesai::text ILIKE ? OR "
                    + "data_master_barang.id_member ILIKE ? OR "
                    + "nama_member ILIKE ? OR "
                    + "nohp_member ILIKE ? ORDER BY id_barang DESC", 6, o).executeQuery();
            while (res.next()) {
                String konstatus = "";
                if (res.getInt("status") == 1) {
                    konstatus = "Belum Dibayar";
                } else {
                    konstatus = "Sudah Dibayar";
                }
                ols.add(new data_barang_masuk_entity(res.getString("id_barang"),
                        res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                        res.getString("id_member"), res.getString("nama_member"),
                        res.getString("nohp_member"), nf.format(res.getDouble("uangmuka")),
                        konstatus));
            }
            ResultSet res2 = m.read("SELECT COUNT(id_barang) AS total FROM data_master_barang WHERE status !=0").executeQuery();
            res2.next();
            ltotal.setText("Menampilkan " + ols.size() + " Dari " + res2.getString("total") + " Data ");

            m.disconnect();
            id_barang.setCellValueFactory(new PropertyValueFactory<>("id_barang"));
            tanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
            tanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
            id_member.setCellValueFactory(new PropertyValueFactory<>("id_member"));
            nama_member.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            nohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            uangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            tbview.setItems(ols);

        } catch (SQLException ex) {
            Logger.getLogger(Data_barang_selesaiController.class
                    .getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void cari() {
        tcari.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (event.getCode().equals(KeyCode.ENTER)) {
                    rawcari();
                }
            }
        });
    }

    private void clear() {
        bclear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                    Alert alc = new Alert(Alert.AlertType.CONFIRMATION);
                    alc.setTitle("Konfirmasi");
                    alc.setHeaderText("Data yang dipilih akan dihapus dari database");
                    alc.setContentText("Yakin ingin melanjutkan ?");
                    ButtonType ok = new ButtonType("Ya");
                    ButtonType no = new ButtonType("Tidak");
                    alc.getButtonTypes().setAll(ok, no);
                    Optional<ButtonType> resb = alc.showAndWait();
                    if (resb.get() == ok) {
                        ObservableList<data_barang_masuk_entity> selection = tbview.getSelectionModel().getSelectedItems();
                        m.connect();
                        for (int i = 0; i < selection.size(); i++) {
                            m.delete("DELETE FROM data_master_barang WHERE id_barang=?", id_barang.getCellData(selection.get(i)));
                            m.delete("DELETE FROM data_barang_masuk WHERE id_barang=?", id_barang.getCellData(selection.get(i)));
                        }
                        m.disconnect();
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle("Pemberitahuan");
                        al.setHeaderText("Data Berhasil Dihapus");
                        al.setContentText("Refresh Data, jika perubahan belum terlihat");
                        al.showAndWait();
                        viewdata();
                    } else {

                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Data_barang_selesaiController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }
        });
    }

    private void refresh() {
        brefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                viewdata();
            }
        });
    }
    
    public void humancontrol() {
        tbview.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                bclear.setDisable(false);
            }
        });

    }
    
    

}
