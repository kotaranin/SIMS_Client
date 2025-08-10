/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import communication.Communication;
import coordinator.Coordinator;
import domain.ExamPeriod;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.ExamPeriodTM;
import view.ExamPeriodsPanel;

/**
 *
 * @author kotar
 */
public class ExamPeriodsPanelController {

    private final ExamPeriodsPanel examPeriodsPanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public ExamPeriodsPanelController(ExamPeriodsPanel examPeriodsPanel) {
        this.examPeriodsPanel = examPeriodsPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillExamPeriods(List<ExamPeriod> examPeriods) {
        examPeriodsPanel.getTblExamPeriods().setModel(new ExamPeriodTM(examPeriods));
    }

    private void addActionListeners() {
        examPeriodsPanel.getTblExamPeriods().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = examPeriodsPanel.getTblExamPeriods().getSelectedRow() != -1;
                examPeriodsPanel.getBtnDelete().setEnabled(selected);
                examPeriodsPanel.getBtnUpdate().setEnabled(selected);
            }
        });
        examPeriodsPanel.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
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
        });
        examPeriodsPanel.getTxtStartDate().getDocument().addDocumentListener(new DocumentListener() {
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
        });
        examPeriodsPanel.getTxtEndDate().getDocument().addDocumentListener(new DocumentListener() {
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
        });
        examPeriodsPanel.deleteAddActionListener((ActionEvent e) -> {
            try {
                int row = examPeriodsPanel.getTblExamPeriods().getSelectedRow();
                ExamPeriod examPeriod = (ExamPeriod) ((ExamPeriodTM) examPeriodsPanel.getTblExamPeriods().getModel()).getValueAt(row, 1);
                communication.deleteExamPeriod(examPeriod);
                JOptionPane.showMessageDialog(examPeriodsPanel, "Sistem je obrisao ispitni rok.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillExamPeriods(communication.getAllExamPeriods());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        examPeriodsPanel.insertAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertExamPeriodForm(null, Mode.INSERT);
                fillExamPeriods(communication.getAllExamPeriods());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        examPeriodsPanel.updateAddActionListener((ActionEvent e) -> {
            try {
                int row = examPeriodsPanel.getTblExamPeriods().getSelectedRow();
                ExamPeriod examPeriod = (ExamPeriod) ((ExamPeriodTM) examPeriodsPanel.getTblExamPeriods().getModel()).getValueAt(row, 1);
                coordinator.openInsertExamPeriodForm(examPeriod, Mode.UPDATE);
                fillExamPeriods(communication.getAllExamPeriods());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void search() {
        try {
            String name = examPeriodsPanel.getTxtName().getText();
            String startDate = examPeriodsPanel.getTxtStartDate().getText();
            String endDate = examPeriodsPanel.getTxtEndDate().getText();
            List<ExamPeriod> examPeriods = communication.searchExamPeriods(" WHERE LOWER(exam_period.name) LIKE LOWER('%" + name + "%') AND exam_period.start_date LIKE '%" + startDate + "%' AND exam_period.end_date LIKE '%" + endDate + "%'");
            fillExamPeriods(examPeriods);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

}
