/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudyLevel;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.StudyLevelTM;
import view.panels.StudyLevelPanel;

/**
 *
 * @author Uros
 */
public class StudyLevelPanelController {

    private final StudyLevelPanel studyLevelPanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public StudyLevelPanelController(StudyLevelPanel studyLevelPanel) {
        this.studyLevelPanel = studyLevelPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillStudyLevels(List<StudyLevel> studyLevels) {
        studyLevelPanel.getTblStudyLevels().setModel(new StudyLevelTM(studyLevels));
    }

    private void addActionListeners() {
        studyLevelPanel.getTblStudyLevels().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = studyLevelPanel.getTblStudyLevels().getSelectedRow() != -1;
                studyLevelPanel.getBtnDelete().setEnabled(selected);
                studyLevelPanel.getBtnUpdate().setEnabled(selected);
            }
        });
        studyLevelPanel.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
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
                    String name = studyLevelPanel.getTxtName().getText();
                    List<StudyLevel> studyLevels = communication.searchStudyLevels(" WHERE LOWER(name) LIKE LOWER('%" + name + "%')");
                    fillStudyLevels(studyLevels);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        studyLevelPanel.deleteAddActionListener((ActionEvent e) -> {
            try {
                int row = studyLevelPanel.getTblStudyLevels().getSelectedRow();
                StudyLevel studyLevel = (StudyLevel) ((StudyLevelTM) studyLevelPanel.getTblStudyLevels().getModel()).getValueAt(row, 1);
                communication.deleteStudyLevel(studyLevel);
                JOptionPane.showMessageDialog(studyLevelPanel, "Sistem je obrisao nivo studija", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillStudyLevels(communication.getAllStudyLevels());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        studyLevelPanel.insertAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertStudyLevelForm(null, Mode.INSERT);
                fillStudyLevels(communication.getAllStudyLevels());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        studyLevelPanel.updateAddActionListener((ActionEvent e) -> {
            try {
                int row = studyLevelPanel.getTblStudyLevels().getSelectedRow();
                StudyLevel studyLevel = (StudyLevel) ((StudyLevelTM) studyLevelPanel.getTblStudyLevels().getModel()).getValueAt(row, 1);
                coordinator.openInsertStudyLevelForm(studyLevel, Mode.UPDATE);
                fillStudyLevels(communication.getAllStudyLevels());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
