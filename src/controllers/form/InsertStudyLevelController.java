/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudyLevel;
import domain.StudyProgram;
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
import table_models.StudyProgramTM;
import view.forms.InsertStudyLevelForm;

/**
 *
 * @author Uros
 */
public class InsertStudyLevelController {

    private final InsertStudyLevelForm insertStudyLevelForm;
    private final StudyLevel studyLevel;
    private final Mode mode;
    private final Communication communication;
    private final Coordinator coordinator;

    public InsertStudyLevelController(InsertStudyLevelForm insertStudyLevelForm, StudyLevel studyLevel, Mode mode) {
        this.insertStudyLevelForm = insertStudyLevelForm;
        this.studyLevel = studyLevel;
        this.mode = mode;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillStudyPrograms(List<StudyProgram> studyPrograms) {
        insertStudyLevelForm.getTblStudyProgram().setModel(new StudyProgramTM(studyPrograms));
    }

    public void fillModules(List<domain.Module> modules) {
        insertStudyLevelForm.getTblModule().setModel(new ModuleTM(modules));
    }

    public void openInsertStudyLevelForm() throws Exception {
        if (mode == Mode.UPDATE) {
            insertStudyLevelForm.getTxtStudyLevelName().setText(studyLevel.getName());
            fillStudyPrograms(studyLevel.getStudyPrograms());
            fillModules(new LinkedList<>());
            insertStudyLevelForm.setTitle("Azuriraj nivo studija");
        } else {
            fillStudyPrograms(new LinkedList<>());
            fillModules(new LinkedList<>());
            insertStudyLevelForm.setTitle("Kreiraj nivo studija");
        }
        insertStudyLevelForm.setLocationRelativeTo(insertStudyLevelForm.getParent());
        insertStudyLevelForm.setVisible(true);
    }

    public void closeInsertStudyLevelForm() {
        insertStudyLevelForm.dispose();
    }

    public void insert(StudyProgram studyProgram) {
        ((StudyProgramTM) insertStudyLevelForm.getTblStudyProgram().getModel()).insert(studyProgram);
    }

    public void update(StudyProgram studyProgram) {
        ((StudyProgramTM) insertStudyLevelForm.getTblStudyProgram().getModel()).update(studyProgram);
    }

    private void addActionListeners() {
        insertStudyLevelForm.getTblStudyProgram().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = insertStudyLevelForm.getTblStudyProgram().getSelectedRow() != -1;
                insertStudyLevelForm.getBtnUpdateStudyProgram().setEnabled(selected);
            }
        });
        insertStudyLevelForm.getTblStudyProgram().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int row = insertStudyLevelForm.getTblStudyProgram().getSelectedRow();
                    StudyProgram studyProgram = (StudyProgram) ((StudyProgramTM) insertStudyLevelForm.getTblStudyProgram().getModel()).getValueAt(row, 1);
                    fillModules(studyProgram.getModules());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        insertStudyLevelForm.insertStudyProgramAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertStudyProgramForm(null, Mode.INSERT);
                fillModules(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudyLevelForm.updateStudyProgramAddActionListener((ActionEvent e) -> {
            try {
                int row = insertStudyLevelForm.getTblStudyProgram().getSelectedRow();
                StudyProgram studyProgram = (StudyProgram) ((StudyProgramTM) insertStudyLevelForm.getTblStudyProgram().getModel()).getValueAt(row, 1);
                coordinator.openInsertStudyProgramForm(studyProgram, Mode.UPDATE);
                fillModules(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudyLevelForm.getTxtStudyProgramName().getDocument().addDocumentListener(new DocumentListener() {
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

            }
        });
        insertStudyLevelForm.saveAddActionListener((ActionEvent e) -> {
            String name = insertStudyLevelForm.getTxtStudyLevelName().getText();
            List<StudyProgram> studyPrograms = ((StudyProgramTM) insertStudyLevelForm.getTblStudyProgram().getModel()).getList();
            switch (mode) {
                case INSERT -> {
                    try {
                        StudyLevel sl = new StudyLevel(null, name, studyPrograms);
                        communication.insertStudyLevel(sl);
                        JOptionPane.showMessageDialog(insertStudyLevelForm, "Sistem je zapamtio nivo studija.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudyLevelForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        studyLevel.setName(name);
                        studyLevel.setStudyPrograms(studyPrograms);
                        communication.updateStudyLevel(studyLevel);
                        JOptionPane.showMessageDialog(insertStudyLevelForm, "Sistem je zapamtio nivo studija.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudyLevelForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                default ->
                    throw new AssertionError();
            }
        });
    }

}
