/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_akun_entity;
import Entity.Homecucian;
import Entity.data_barang_masuk_entity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Model;

/**
 * FXML Controller class
 *
 * @author Mulyadi
 */
public class HomeController implements Initializable {

    @FXML
    private TableView<Homecucian> tbmasuk, tbselesai;
    @FXML
    private TableColumn<Homecucian, String> masuktanggal_masuk,masuktanggal_selesai, masuknama, masuknohp, masukuangmuka,
            selesaitanggal_masuk,selesaitanggal_selesai, selesainama, selesainohp, selesaiuangmuka;
    @FXML
    private LineChart<String, Number> gmasuk,gkeluar;
    ObservableList ols1 = FXCollections.observableArrayList();
    ObservableList ols2 = FXCollections.observableArrayList();
    Model m = new Model();
    int offset, limit;
    NumberFormat nf = NumberFormat.getInstance();
    Errorhandler err=new Errorhandler();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadtbmasuk();
        loadtbselesai();
        loadgrafikmasuk();
        loadgrafikkeluar();
    }

    private void loadtbmasuk() {
        tbmasuk.getItems().clear();
        tbmasuk.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            m.connect();
            ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                    + "data_master_barang.id_member as id_member,"
                    + "nama_member,nohp_member,uangmuka FROM data_master_barang LEFT JOIN "
                    + "data_member ON data_master_barang.id_member = data_member.id_member "
                    + "WHERE status=0 "
                    + "ORDER BY id_barang DESC").executeQuery();
            while (res.next()) {
                ols1.add(new data_barang_masuk_entity(res.getString("id_barang"),
                        res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                        res.getString("id_member"), res.getString("nama_member"),
                        res.getString("nohp_member"), nf.format(res.getDouble("uangmuka"))));

            }
            m.disconnect();
            masuktanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
            masuktanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
            masuknama.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            masuknohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            masukuangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
            tbmasuk.setItems(ols1);

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void loadtbselesai() {
        tbselesai.getItems().clear();
        tbselesai.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            m.connect();
            ResultSet res = m.read("SELECT id_barang,tanggal_masuk::date,tanggal_selesai,"
                    + "data_master_barang.id_member as id_member,"
                    + "nama_member,nohp_member,uangmuka FROM data_master_barang LEFT JOIN "
                    + "data_member ON data_master_barang.id_member = data_member.id_member "
                    + "WHERE status=1 ORDER BY id_barang DESC").executeQuery();
            while (res.next()) {
                ols2.add(new data_barang_masuk_entity(res.getString("id_barang"),
                        res.getString("tanggal_masuk"), res.getString("tanggal_selesai"),
                        res.getString("id_member"), res.getString("nama_member"),
                        res.getString("nohp_member"), nf.format(res.getDouble("uangmuka"))));

            }
            m.disconnect();
            selesaitanggal_masuk.setCellValueFactory(new PropertyValueFactory<>("tanggal_masuk"));
            selesaitanggal_selesai.setCellValueFactory(new PropertyValueFactory<>("tanggal_selesai"));
            selesainama.setCellValueFactory(new PropertyValueFactory<>("nama_member"));
            selesainohp.setCellValueFactory(new PropertyValueFactory<>("nohp"));
            selesaiuangmuka.setCellValueFactory(new PropertyValueFactory<>("uangmuka"));
            tbselesai.setItems(ols2);

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

    private void loadgrafikmasuk() {
        gmasuk.setLegendVisible(false);
        try {
            String query = "SELECT EXTRACT(DAY FROM tanggal) as tanggal,SUM(jumlah) AS total FROM data_journal "
                    + "INNER JOIN data_biaya ON data_journal.id_biaya=data_biaya.id_biaya WHERE kategori_biaya='Pendapatan' "
                    + "AND EXTRACT(MONTH FROM tanggal)=EXTRACT(MONTH FROM now())   GROUP BY tanggal";
            XYChart.Series barseries = new XYChart.Series();
            m.connect();
            ResultSet res = m.read(query).executeQuery();
            while (res.next()) {
                barseries.getData().add(new XYChart.Data(res.getString("tanggal"), res.getDouble("total")));
            }
            gmasuk.getData().addAll(barseries);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

    }

    private void loadgrafikkeluar() {
        gkeluar.setLegendVisible(false);
        try {
            String query = "SELECT EXTRACT(DAY FROM tanggal) as tanggal,SUM(jumlah) AS total FROM data_journal "
                    + "INNER JOIN data_biaya ON data_journal.id_biaya=data_biaya.id_biaya WHERE kategori_biaya='Pengeluaran' "
                    + "AND EXTRACT(MONTH FROM tanggal)=EXTRACT(MONTH FROM now())   GROUP BY tanggal";
            XYChart.Series barseries = new XYChart.Series();
            m.connect();
            ResultSet res = m.read(query).executeQuery();
            while (res.next()) {
                barseries.getData().add(new XYChart.Data(res.getString("tanggal"), res.getDouble("total")));
            }
            gkeluar.getData().addAll(barseries);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }

    }
}
