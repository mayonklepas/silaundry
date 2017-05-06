/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Data_akun_entity;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import model.Model;
import org.smslib.OutboundMessage;
import org.smslib.Service;

/**
 * FXML Controller class
 *
 * @author Minami
 */
public class Form_smsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tnohp;
    @FXML
    private TextArea tpesan;
    @FXML
    private Button bkirim, bclear;
    public static String nohp;
    
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        kirim();
        bersihkan();
        tnohp.setText(nohp);
        tnohp.requestFocus();
        bkirim.setGraphic(new ImageView(getClass().getResource("/image/mail-outbox.png").toString()));
        bkirim.setTooltip(new Tooltip("Kirim Sms"));
        bclear.setGraphic(new ImageView(getClass().getResource("/image/edit-clear-3.png").toString()));
        bclear.setTooltip(new Tooltip("Clear field"));
        
    }

    private void kirim() {
        bkirim.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                VBox vb = new VBox();
                vb.setPrefSize(300, 0);
                vb.setSpacing(5);
                vb.setPadding(new Insets(5, 5, 5, 5));
                ProgressBar pg = new ProgressBar();
                pg.setMaxWidth(Double.MAX_VALUE);
                vb.getChildren().add(pg);
                Scene sc = new Scene(vb);
                Stage st = new Stage(StageStyle.UNDECORATED);
                st.setTitle("Memproses Data");
                st.setScene(sc);

                kirimtask kt = new kirimtask(tnohp.getText(),tpesan.getText());
                kt.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        st.close();
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle("Pemberitahuan");
                        al.setHeaderText("Operasi Sukses");
                        al.setContentText("Pengiriman Pesan Berhasil");
                        al.show();
                    }
                });
                pg.progressProperty().unbind();
                pg.progressProperty().bind(kt.progressProperty());
                Thread th = new Thread(kt);
                th.setDaemon(true);
                th.start();
                st.showAndWait();

            }
        });

    }

    public void bersihkan() {
        bclear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tnohp.clear();
                tpesan.clear();
            }
        });
    }

    public class kirimtask extends Task<Void> {
        
        String nohp,pesan;
        
        public kirimtask(String nohp,String pesan) {
            this.nohp=nohp;
            this.pesan=pesan;
        }
        
        

        @Override
        protected Void call() throws Exception {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            OutboundMessage om = new OutboundMessage(nohp, pesan);
            long start = System.currentTimeMillis();
            Service.getInstance().sendMessage(om);
            long stop = System.currentTimeMillis();
            updateProgress(start, stop);
            updateMessage(" SMS Terkirim ");
            return null;
        }

    }

}
