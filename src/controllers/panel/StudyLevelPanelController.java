/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudyLevel;
import domain.StudyProgram;
import domain.Module;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.ModuleTM;
import table_models.StudyLevelTM;
import table_models.StudyProgramTM;
import view.panels.StudyLevelPanel;

/**
 *
 * @author Uros
 */
public class StudyLevelPanelController {

    private final StudyLevelPanel studyLevelPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private List<StudyLevel> allStudyLevels;

    public StudyLevelPanelController(StudyLevelPanel studyLevelPanel) {
        this.studyLevelPanel = studyLevelPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public List<StudyLevel> getAllStudyLevels() {
        return allStudyLevels;
    }

    public void setAllStudyLevels(List<StudyLevel> allStudyLevels) {
        this.allStudyLevels = allStudyLevels;
    }

    public void fillStudyLevels(List<StudyLevel> studyLevels) {
        studyLevelPanel.getTblStudyLevel().setModel(new StudyLevelTM(studyLevels));
    }

    public void fillStudyPrograms(List<StudyProgram> studyPrograms) {
        studyLevelPanel.getTblStudyProgram().setModel(new StudyProgramTM(studyPrograms));
    }

    public void fillModules(List<Module> modules) {
        studyLevelPanel.getTblModule().setModel(new ModuleTM(modules));
    }

    private void addActionListeners() {
        studyLevelPanel.getTblStudyLevel().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = studyLevelPanel.getTblStudyLevel().getSelectedRow() != -1;
                studyLevelPanel.getBtnUpdate().setEnabled(selected);
            }
        });
        studyLevelPanel.getTblStudyLevel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int row = studyLevelPanel.getTblStudyLevel().getSelectedRow();
                    StudyLevel studyLevel = (StudyLevel) ((StudyLevelTM) studyLevelPanel.getTblStudyLevel().getModel()).getValueAt(row, 0);
                    fillStudyPrograms(studyLevel.getStudyPrograms());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        studyLevelPanel.getTblStudyProgram().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int row = studyLevelPanel.getTblStudyProgram().getSelectedRow();
                    StudyProgram studyProgram = (StudyProgram) ((StudyProgramTM) studyLevelPanel.getTblStudyProgram().getModel()).getValueAt(row, 0);
                    fillModules(studyProgram.getModules());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
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
                    String name = studyLevelPanel.getTxtName().getText().trim();
                    List<StudyLevel> filteredStudyLevels = (List<StudyLevel>) allStudyLevels.stream().filter(studyLevel -> studyLevel.getName().toLowerCase().contains(name)).toList();
                    fillStudyLevels(new LinkedList<>(filteredStudyLevels));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        studyLevelPanel.insertAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertStudyLevelForm(null, Mode.INSERT);
                fillStudyLevels(communication.getAllStudyLevels());
                fillStudyPrograms(null);
                fillModules(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        studyLevelPanel.updateAddActionListener((ActionEvent e) -> {
            try {
                int row = studyLevelPanel.getTblStudyLevel().getSelectedRow();
                StudyLevel studyLevel = (StudyLevel) ((StudyLevelTM) studyLevelPanel.getTblStudyLevel().getModel()).getValueAt(row, 0);
                coordinator.openInsertStudyLevelForm(studyLevel, Mode.UPDATE);
                fillStudyLevels(communication.getAllStudyLevels());
                fillStudyPrograms(communication.getStudyPrograms(studyLevel));
                fillModules(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studyLevelPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
