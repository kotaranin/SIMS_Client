/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.ExamPeriod;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.ExamPeriodTM;
import view.panels.ExamPeriodPanel;

/**
 *
 * @author kotar
 */
public class ExamPeriodPanelController {

    private final ExamPeriodPanel examPeriodsPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private boolean initialized;

    public ExamPeriodPanelController(ExamPeriodPanel examPeriodsPanel) {
        this.examPeriodsPanel = examPeriodsPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        initialized = false;
        examPeriodsPanel.getTxtName().setText(null);
        fillExamPeriods(communication.getAllExamPeriods());
        initialized = true;
    }

    private void fillExamPeriods(List<ExamPeriod> examPeriods) {
        examPeriodsPanel.getTblExamPeriods().setModel(new ExamPeriodTM(examPeriods));
    }

    private void addActionListeners() {
        examPeriodsPanel.getTblExamPeriods().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = examPeriodsPanel.getTblExamPeriods().getSelectedRow() != -1;
                examPeriodsPanel.getBtnUpdate().setEnabled(selected);
            }
        });
        examPeriodsPanel.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        examPeriodsPanel.insertAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertExamPeriodForm(null, Mode.INSERT);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        examPeriodsPanel.updateAddActionListener((ActionEvent e) -> {
            try {
                int row = examPeriodsPanel.getTblExamPeriods().getSelectedRow();
                ExamPeriod examPeriod = (ExamPeriod) ((ExamPeriodTM) examPeriodsPanel.getTblExamPeriods().getModel()).getValueAt(row, 0);
                coordinator.openInsertExamPeriodForm(examPeriod, Mode.UPDATE);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void search() {
        try {
            String name = examPeriodsPanel.getTxtName().getText().trim();
            ExamPeriod examPeriod = new ExamPeriod(null, name, null, null);
            List<ExamPeriod> examPeriods = communication.searchExamPeriods(examPeriod);
            fillExamPeriods(examPeriods);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(examPeriodsPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

}
