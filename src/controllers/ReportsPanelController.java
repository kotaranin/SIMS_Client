/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import communication.Communication;
import coordinator.Coordinator;
import domain.Report;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
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

    public void fillReports(List<Report> reports) {
        reportsPanel.getTblReports().setModel(new ReportTableModel(reports));
    }

    private void addActionListeners() {
        reportsPanel.getTblReports().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = reportsPanel.getTblReports().getSelectedRow() != -1;
                reportsPanel.getBtnDeleteReport().setEnabled(selected);
                reportsPanel.getBtnUpdateReport().setEnabled(selected);
            }
        });
        reportsPanel.getTxtFileName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void search() {
                try {
                    String fileName = reportsPanel.getTxtFileName().getText();
                    fillReports(communication.searchReports(" WHERE LOWER(file_name) LIKE LOWER('%" + fileName + "%')"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        reportsPanel.deleteReportAddActionListener((ActionEvent e) -> {
            try {
                int row = reportsPanel.getTblReports().getSelectedRow();
                Report report = (Report) ((ReportTableModel) reportsPanel.getTblReports().getModel()).getValueAt(row, 1);
                communication.deleteReport(report);
                JOptionPane.showMessageDialog(reportsPanel, "Sistem je obrisao dnevnik prakse.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillReports(communication.getAllReports());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        reportsPanel.insertReportAddActionListener((ActionEvent e) -> {
            try {
                Report report = Coordinator.getInstance().openFilePickerForm();
                communication.insertReport(report);
                JOptionPane.showMessageDialog(reportsPanel, "Sistem je zapamtio dnevnik prakse.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillReports(communication.getAllReports());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        reportsPanel.updateReportAddActionListener((ActionEvent e) -> {
            try {
                Report selectedReport = Coordinator.getInstance().openFilePickerForm();
                int row = reportsPanel.getTblReports().getSelectedRow();
                Report report = (Report) reportsPanel.getTblReports().getValueAt(row, 1);
                report.setFileName(selectedReport.getFileName());
                report.setFileContent(selectedReport.getFileContent());
                communication.updateReport(report);
                JOptionPane.showMessageDialog(reportsPanel, "Sistem je zapamtio dnevnik prakse.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillReports(communication.getAllReports());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
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
