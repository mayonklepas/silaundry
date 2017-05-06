/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Model;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author minami
 */
public class GrafikController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button blihat;
    @FXML
    private BarChart<String, Number> barchart;
    @FXML
    private CategoryAxis barcategory;
    @FXML
    private NumberAxis barnumber;

    @FXML
    private PieChart piechart;

    @FXML
    private ComboBox ckategori;
    @FXML
    private DatePicker ddari, dsampai;

    Model m = new Model();
    Filecontrol fc = new Filecontrol();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lihatgrafik();

        blihat.setGraphic(new ImageView(getClass().getResource("/image/office-chart-line.png").toString()));
        blihat.setTooltip(new Tooltip("Generate Grafik"));
    }

    private void lihatgrafik() {
        blihat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    m.connect();
                    Object[] o = new Object[2];
                    o[0] = ddari.getValue().toString();
                    o[1] = dsampai.getValue().toString();

                    barchart.setTitle("Grafik Pendapatan dan Pengeluaran");
                    XYChart.Series barseries = new XYChart.Series();
                    barseries.setName("Preview Grafik Pertanggal " + ddari.getEditor().getText() + " - " + dsampai.getEditor().getText() + " ");

                    ResultSet resbar = m.readdetail("SELECT kategori_biaya,SUM(jumlah) as jumlah FROM data_journal"
                            + " INNER JOIN data_biaya ON data_journal.id_biaya=data_biaya.id_biaya WHERE "
                            + "tanggal BETWEEN ?::date AND ?::date GROUP BY kategori_biaya", 2, o).executeQuery();
                    while (resbar.next()) {
                        barseries.getData().add(new XYChart.Data(resbar.getString("kategori_biaya"), resbar.getDouble("jumlah")));
                    }
                    barchart.getData().addAll(barseries);

                    ObservableList<PieChart.Data> piedata = FXCollections.observableArrayList();
                    ResultSet respie = m.readdetail("SELECT kategori_biaya,SUM(jumlah) as jumlah FROM data_journal"
                            + " INNER JOIN data_biaya ON data_journal.id_biaya=data_biaya.id_biaya WHERE "
                            + "tanggal BETWEEN ?::date AND ?::date GROUP BY kategori_biaya", 2, o).executeQuery();
                    while (respie.next()) {
                        piedata.add(new PieChart.Data(respie.getString("kategori_biaya"), respie.getDouble("jumlah")));
                    }
                    piechart.setData(piedata);
                    piechart.setTitle("Grafik Pendapatan dan Pengeluaran");

                    m.disconnect();
                } catch (SQLException ex) {
                    Logger.getLogger(GrafikController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public class loadgrafik extends Task<Void> {

        String path;
        HashMap hash;

        public loadgrafik() {
        }

        @Override
        protected Void call() throws Exception {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            try {
                long start = System.currentTimeMillis();
                m.connect();
                barchart.setTitle("Grafik Pendapatan dan Pengeluaran");
                XYChart.Series barseries = new XYChart.Series();
                barseries.setName("Preview Grafik Pertanggal " + ddari.getEditor().getText() + " - " + dsampai.getEditor().getText() + " ");
                Object[] o = new Object[2];
                o[0] = ddari.getValue().toString();
                o[1] = dsampai.getValue().toString();
                ResultSet resbar = m.readdetail("SELECT kategori_biaya,SUM(jumlah) as jumlah FROM data_journal"
                        + " INNER JOIN data_biaya ON data_journal.id_biaya=data_biaya.id_biaya WHERE "
                        + "tanggal BETWEEN ?::date AND ?::date GROUP BY kategori_biaya", 2, o).executeQuery();
                while (resbar.next()) {
                    barseries.getData().add(new XYChart.Data(resbar.getString("kategori_biaya"), resbar.getDouble("jumlah")));
                }
                barchart.getData().addAll(barseries);
                m.disconnect();
                long stop = System.currentTimeMillis();
                updateProgress(start, stop);
                updateMessage("Proses selesai");
            } catch (SQLException ex) {
                Logger.getLogger(GrafikController.class.getName()).log(Level.SEVERE, null, ex);
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Kesalahan");
                al.setHeaderText("Terjadi Kesalahan Pada Aplikasi");
                VBox v = new VBox();
                v.setPadding(new Insets(5, 5, 5, 5));
                v.setSpacing(5);
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                TextArea terror = new TextArea(sw.toString());
                terror.setMaxWidth(400);
                terror.setMaxHeight(400);
                terror.setWrapText(true);
                v.getChildren().add(new Label("Detail error yang terbaca :"));
                v.getChildren().add(terror);
                al.getDialogPane().setContent(v);
                al.showAndWait();
            }
            return null;
        }
    }

}
