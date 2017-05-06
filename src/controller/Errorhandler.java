/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 *
 * @author Mulyadi
 */
public class Errorhandler {

    public Errorhandler() {
    }

    public void showerror(Exception ex) {
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
        terror.setMaxWidth(500);
        terror.setMaxHeight(500);
        terror.setWrapText(true);
        v.getChildren().add(new Label("Detail error yang terbaca :"));
        v.getChildren().add(terror);
        al.getDialogPane().setContent(v);
        al.showAndWait();
    }

}
