/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.StudyLevel;
import enums.Mode;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
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

    public InsertStudyLevelController(InsertStudyLevelForm insertStudyLevelForm, StudyLevel studyLevel, Mode mode) {
        this.insertStudyLevelForm = insertStudyLevelForm;
        this.studyLevel = studyLevel;
        this.mode = mode;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void openInsertStudyLevelForm() {
        if (mode == Mode.UPDATE) {
            insertStudyLevelForm.getTxtName().setText(studyLevel.getName());
            insertStudyLevelForm.setTitle("Azuriraj nivo studija");
        } else {
            insertStudyLevelForm.setTitle("Kreiraj nivo studija");
        }
        insertStudyLevelForm.setLocationRelativeTo(insertStudyLevelForm.getParent());
        insertStudyLevelForm.setVisible(true);
    }

    public void closeInsertStudyForm() {
        insertStudyLevelForm.dispose();
    }

    private void addActionListeners() {
        insertStudyLevelForm.saveAddActionListener((ActionEvent e) -> {
            String name = insertStudyLevelForm.getTxtName().getText();
            switch (mode) {
                case INSERT -> {
                    try {
                        StudyLevel sl = new StudyLevel(null, name);
                        communication.insertStudyLevel(sl);
                        JOptionPane.showMessageDialog(insertStudyLevelForm, "Sistem je zapamtio nivo studija.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudyForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Obavestenje", JOptionPane.ERROR_MESSAGE);
                    }
                }

                case UPDATE -> {
                    try {
                        studyLevel.setName(name);
                        communication.updateStudyLevel(studyLevel);
                        JOptionPane.showMessageDialog(insertStudyLevelForm, "Sistem je zapamtio nivo studija.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudyForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertStudyLevelForm, ex.getMessage(), "Obavestenje", JOptionPane.ERROR_MESSAGE);
                    }
                }

                default ->
                    throw new AssertionError();
            }
        });
    }

}
