/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client_controller;

import communication.Communication;
import domain.Report;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
            JOptionPane.showMessageDialog(reportsPanel, "Sistem nije uspeo da vrati dnevnike prakse.", "Greska", JOptionPane.ERROR_MESSAGE);
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
                try {
                    int row = reportsPanel.getTblReports().getSelectedRow();
                    Report report = (Report) ((ReportTableModel) reportsPanel.getTblReports().getModel()).getValueAt(row, 1);
                    communication.deleteReport(report);
                    JOptionPane.showMessageDialog(reportsPanel, "Sistem je obrisao dnevnik prakse.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                    fillReportsPanel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(reportsPanel, "Sistem ne moze da obrise dnevnik prakse.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        reportsPanel.updateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        reportsPanel.showReportAddMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = reportsPanel.getTblReports().getSelectedRow();
                if (e.getClickCount() == 2) {
                    try {
                        Report report = (Report) reportsPanel.getTblReports().getValueAt(row, 1);
                        File file = File.createTempFile("doc_", "_" + report.getFileName());
                        file.deleteOnExit();
                        FileOutputStream out = new FileOutputStream(file);
                        out.write(report.getFileContent());
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        Logger.getLogger(ReportsPanelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
    }

}
