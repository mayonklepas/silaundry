/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_form_barang_masuk_entity;
import Entity.Data_transaksi_entity;
import com.sun.deploy.util.FXLoader;
import static controller.Form_barang_masukController.id_barang;
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
import model.Model;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Data_transaksiController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param
     */
    @FXML
    private TableView<Data_transaksi_entity> tbview;
    @FXML
    private TableColumn<Data_form_barang_masuk_entity, String> layanan, jumlah, keterangan, biaya, idbarangmasuk, uangmuka;

    @FXML
    private Button boke, bbayar;
    @FXML
    private TextField tcari;
    @FXML
    private Label ltotal,ltotal2;
    ObservableList ols = FXCollections.observableArrayList();
    Model m = new Model();
    int offset, limit;
    NumberFormat nf = NumberFormat.getInstance();
    double totalbiaya;
    Errorhandler err = new Errorhandler();
    double total,pembulatan;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cari();
        bayar();
        tcari.requestFocus();
        tcari.setText("BR" + new SimpleDateFormat("yyMMdd").format(new Date()) + "-");
        bbayar.setGraphic(new ImageView(getClass().getResource("/image/money-2.png").toString()));
        bbayar.setTooltip(new Tooltip("Bayar Transaksi"));
        bbayar.setDisable(true);
    }
    

    private void rawcari() {
        try {
            tbview.getItems().clear();
            Object[] o = new Object[1];
            o[0] = tcari.getText();
            m.connect();
            ResultSet res = m.readdetail("SELECT id,nama_layanan,jumlah,jumlah*tarif_layanan as biaya,keterangan "
                    + "FROM data_barang_masuk LEFT JOIN "
                    + "data_layanan ON data_barang_masuk.id_layanan=data_layanan.id_layanan "
                    + "LEFT JOIN data_master_barang ON "
                    + "data_barang_masuk.id_barang = data_master_barang.id_barang "
                    + "WHERE status !=2 AND data_barang_masuk.id_barang=? ORDER by id DESC",1, o).executeQuery();
            while (res.next()) {
                ols.add(new Data_form_barang_masuk_entity(res.getString("id"), res.getString("nama_layanan"),
                        nf.format(res.getDouble("jumlah")),
                        res.getString("keterangan"),
                        nf.format(res.getDouble("biaya"))));

            }
            ResultSet res2 = m.readdetail("SELECT SUM(jumlah*tarif_layanan) AS total FROM data_barang_masuk "
                    + "LEFT JOIN data_layanan ON data_barang_masuk.id_layanan=data_layanan.id_layanan "
                    + "LEFT JOIN data_master_barang ON "
                    + "data_barang_masuk.id_barang = data_master_barang.id_barang "
                    + "WHERE status !=2 AND data_barang_masuk.id_barang=? ", 1, o).executeQuery();
            while (res2.next()) {                
                total = res2.getDouble("total");
            }
            
            
            ResultSet res3 = m.readdetail("SELECT pembulatan FROM data_master_barang WHERE status !=2 AND id_barang=?", 1, o).executeQuery();
            while (res3.next()) {                
                pembulatan = res3.getDouble("pembulatan");
            }
            
            if(total==0){
                ltotal.setText("Total : Rp " + nf.format(total));
            }else{
                ltotal.setText("Total : Rp " + nf.format(total+pembulatan));
            }
            
            
            

            m.disconnect();
            idbarangmasuk.setCellValueFactory(new PropertyValueFactory<>("idbarangmasuk"));
            layanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
            jumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
            keterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
            biaya.setCellValueFactory(new PropertyValueFactory<>("biaya"));
            tbview.setItems(ols);

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(Data_transaksiController.class.getName()).log(Level.SEVERE, null, ex);
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
                    if (total==0){
                        bbayar.setDisable(true);
                    }else{
                        bbayar.setDisable(false);
                    }
                }
            }
        });
    }

    private void bayar() {
        bbayar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    m.connect();
                    Object[] o = new Object[1];
                    o[0] = tcari.getText();
                    ResultSet res = m.readdetail("SELECT uangmuka FROM data_master_barang "
                            + "WHERE id_barang=?", 1, o).executeQuery();
                    res.next();
                    m.disconnect();
                    double lokaluangmuka = res.getDouble("uangmuka");
                    FormbayarController.total = total+pembulatan;
                    FormbayarController.uangmuka = lokaluangmuka;
                    FormbayarController.id_barang = tcari.getText();
                    Parent pr = FXMLLoader.load(getClass().getResource("/view/Formbayar.fxml"));
                    Scene sc = new Scene(pr);
                    Stage st = new Stage();
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.setTitle("Form Pembayaran");
                    st.setScene(sc);
                    st.showAndWait();
                    tcari.setText("BR" + new SimpleDateFormat("yyMMdd").format(new Date()) + "-");
                    rawcari();
                    ltotal.setText("Total : Rp. 0");
                    tcari.requestFocus();
                    bbayar.setDisable(true);
                } catch (IOException ex) {
                    Logger.getLogger(Data_transaksiController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Data_transaksiController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

}
