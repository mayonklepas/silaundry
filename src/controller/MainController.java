/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entity.Sessionuser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author Minami
 */
public class MainController implements Initializable {

    @FXML
    private TabPane tabpane;
    @FXML
    private MenuItem mkeluar, maturmodem, maturdatabase, maturinfo, mtentangapp, mhubungikami;
    @FXML
    private AnchorPane container;
    @FXML
    private Pane dmaster, dtransaksi, dlaporan;
    @FXML
    private ImageView bhome;

    File image = new File("bg.jpg");
    int statmaster = 0, stattransaksi = 0, statlaporan = 0;
    Sessionuser ses=new Sessionuser();
    Errorhandler err=new Errorhandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*if(ses.getTipe()=="User"){
            dlaporan.setVisible(false);
        }else{
            dlaporan.setVisible(true);
        }*/
        menuselect();
        masterdata();
        transaksi();
        laporan();
        home();
        homeload();

        mkeluar.setGraphic(new ImageView(getClass().getResource("/image/application-exit-4.png").toString()));
        maturdatabase.setGraphic(new ImageView(getClass().getResource("/image/database-gear.png").toString()));
        maturmodem.setGraphic(new ImageView(getClass().getResource("/image/system-settings.png").toString()));
        mtentangapp.setGraphic(new ImageView(getClass().getResource("/image/emblem-notice.png").toString()));
        mhubungikami.setGraphic(new ImageView(getClass().getResource("/image/vcard.png").toString()));
        //mmanual.setGraphic(new ImageView(getClass().getResource("/image/book-open.png").toString()));
        maturinfo.setGraphic(new ImageView(getClass().getResource("/image/lightbulb.png").toString()));

        hover();

    }

    private void hover() {
        dmaster.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                dmaster.setStyle("-fx-background-color:#777");
            }
        });
        dmaster.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (statmaster == 1) {
                    dmaster.setStyle("-fx-background-color:#222");
                } else {
                    dmaster.setStyle("-fx-background-color:#464646");
                }

            }
        });

        dtransaksi.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                dtransaksi.setStyle("-fx-background-color:#777");
            }
        });
        dtransaksi.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (stattransaksi == 1) {
                    dtransaksi.setStyle("-fx-background-color:#222");
                } else {
                    dtransaksi.setStyle("-fx-background-color:#464646");
                }
            }
        });

        dlaporan.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                dlaporan.setStyle("-fx-background-color:#777");
            }
        });
        dlaporan.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (statlaporan == 1) {
                    dlaporan.setStyle("-fx-background-color:#222");
                } else {
                    dlaporan.setStyle("-fx-background-color:#464646");
                }
            }
        });

    }

    public void menuselect() {
        mkeluar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                System.exit(0);
            }
        });

        maturdatabase.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Stage st = new Stage();
                    st.setTitle("Pengaturan Database");
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Pengaturan_database.fxml"));
                    Scene sc = new Scene(root);
                    st.setScene(sc);
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.resizableProperty().setValue(Boolean.FALSE);
                    st.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });

        maturmodem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Stage st = new Stage();
                    st.setTitle("Pengaturan Modem");
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Pengaturan_modem.fxml"));
                    Scene sc = new Scene(root);
                    st.setScene(sc);
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.resizableProperty().setValue(Boolean.FALSE);
                    st.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });

        mtentangapp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Tentangapp.fxml"));
                    Scene sc = new Scene(root);
                    Stage st = new Stage();
                    st.setTitle("Tentang Aplikasi");
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.resizableProperty().setValue(Boolean.FALSE);
                    st.setScene(sc);
                    st.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });

        mhubungikami.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Hubungikami.fxml"));
                    Scene sc = new Scene(root);
                    Stage st = new Stage();
                    st.setTitle("Hubungi Kami");
                    st.initModality(Modality.APPLICATION_MODAL);
                    st.resizableProperty().setValue(Boolean.FALSE);
                    st.setScene(sc);
                    st.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });

        /*  mmanual.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                System.exit(0);
            }
        });*/
        maturinfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    Stage st = new Stage();
                    st.setTitle("Pengaturan Info");
                    st.initModality(Modality.APPLICATION_MODAL);
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Data_info.fxml"));
                    Scene sc = new Scene(root);
                    st.setScene(sc);
                    st.showAndWait();
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });

    }

    private void masterdata() {
        dmaster.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    statmaster = 1;
                    stattransaksi = 0;
                    statlaporan = 0;
                    dmaster.setStyle("-fx-background-color:#222");
                    dtransaksi.setStyle("-fx-background-color:#464646");
                    dlaporan.setStyle("-fx-background-color:#464646");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/mastercontainer.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    container.getChildren().clear();
                    container.getChildren().add(node);
                    node.prefWidthProperty().bind(container.widthProperty());
                    node.prefHeightProperty().bind(container.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void transaksi() {
        dtransaksi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    statmaster = 0;
                    stattransaksi = 1;
                    statlaporan = 0;
                    dmaster.setStyle("-fx-background-color:#464646");
                    dtransaksi.setStyle("-fx-background-color:#222");
                    dlaporan.setStyle("-fx-background-color:#464646");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/transaksicontainer.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    container.getChildren().clear();
                    container.getChildren().add(node);
                    node.prefWidthProperty().bind(container.widthProperty());
                    node.prefHeightProperty().bind(container.heightProperty());
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void laporan() {
        dlaporan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    statmaster = 0;
                    stattransaksi = 0;
                    statlaporan = 1;
                    dmaster.setStyle("-fx-background-color:#464646");
                    dtransaksi.setStyle("-fx-background-color:#464646");
                    dlaporan.setStyle("-fx-background-color:#222");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Laporan.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    container.getChildren().clear();
                    container.getChildren().add(node);
                    node.prefWidthProperty().bind(container.widthProperty());
                    node.prefHeightProperty().bind(container.heightProperty());

                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void home() {
        bhome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    statmaster = 0;
                    stattransaksi = 0;
                    statlaporan = 0;
                    dmaster.setStyle("-fx-background-color:#464646");
                    dtransaksi.setStyle("-fx-background-color:#464646");
                    dlaporan.setStyle("-fx-background-color:#464646");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
                    AnchorPane node = (AnchorPane) fxmlLoader.load();
                    container.getChildren().clear();
                    container.getChildren().add(node);
                    node.prefWidthProperty().bind(container.widthProperty());
                    node.prefHeightProperty().bind(container.heightProperty());

                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }
            }
        });
    }

    private void homeload() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
            AnchorPane node = (AnchorPane) fxmlLoader.load();
            container.getChildren().clear();
            container.getChildren().add(node);
            node.prefWidthProperty().bind(container.widthProperty());
            node.prefHeightProperty().bind(container.heightProperty());

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            err.showerror(ex);
        }
    }

}
