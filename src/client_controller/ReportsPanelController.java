/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client_controller;

import communication.Communication;
import domain.Report;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.ReportTableModel;
import view.ReportsPanel;

/**
 *
 * @author kotar
 */
public class ReportsPanelController {

    private final ReportsPanel reportsPanel;
    private final Communication communication;

    public ReportsPanelController(ReportsPanel reportsPanel) {
        this.reportsPanel = reportsPanel;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void fillReportsPanel()  {
        try {
            List<Report> reports = communication.getAllReports();
            reportsPanel.getTblReports().setModel(new ReportTableModel(reports));
        } catch (Exception ex) {
            Logger.getLogger(ReportsPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addActionListeners() {
        reportsPanel.getTxtFileName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        reportsPanel.deleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        reportsPanel.updateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

}
