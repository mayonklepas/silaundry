/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import model.Model;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author minami
 */
public class Devicecontrol extends Task<Void> {

    Filecontrol fc=new Filecontrol();
    static Errorhandler err=new Errorhandler();
    @Override
    protected Void call() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            Inbound in = new Inbound();
            Outbound out = new Outbound();
            SerialModemGateway g = new SerialModemGateway(fc.modem(), fc.commport(),
                    Integer.parseInt(fc.bautrate()), fc.vendor(), fc.tipe());
            g.setInbound(true);
            g.setOutbound(true);
            g.setSimPin(fc.simpin());
            g.setSmscNumber(fc.smscenter());
            Service.getInstance().setInboundMessageNotification(in);
            Service.getInstance().setOutboundMessageNotification(out);
            Service.getInstance().addGateway(g);
            Service.getInstance().startService();
        } catch (NumberFormatException | SMSLibException | IOException | InterruptedException ex) {
            Logger.getLogger(Devicecontrol.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            err.showerror(ex);
        }

        return null;
    }

    private static class Inbound implements IInboundMessageNotification {

        public Inbound() {
        }

        @Override
        public void process(AGateway ag, Message.MessageTypes mt, InboundMessage im) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (mt == Message.MessageTypes.INBOUND) {
                JOptionPane.showMessageDialog(null, im.getDate() + "\n" + im.getOriginator() + "\n" + im.getText());
                Model m = new Model();
                try {
                    m.connect();
                    Object[] o = new Object[3];
                    o[0] = String.valueOf(im.getDate());
                    o[1] = im.getOriginator();
                    o[2] = im.getText();
                    m.insert("INSERT INTO data_sms(tanggal,nohp,pesan) VALUES(?,?,?)", 3, o);
                    m.disconnect();
                    try {
                        ag.deleteMessage(im);
                    } catch (TimeoutException | GatewayException | IOException | InterruptedException ex) {
                        Logger.getLogger(Devicecontrol.class.getName()).log(Level.SEVERE, null, ex);
                       err.showerror(ex);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Devicecontrol.class.getName()).log(Level.SEVERE, null, ex);
                    err.showerror(ex);
                }

            }

        }
    }

    private static class Outbound implements IOutboundMessageNotification {

        public Outbound() {
        }

        @Override
        public void process(AGateway ag, OutboundMessage om) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
