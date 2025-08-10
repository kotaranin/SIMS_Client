/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.Teacher;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.forms.InsertTeacherForm;

/**
 *
 * @author kotar
 */
public class InsertTeacherController {

    private final InsertTeacherForm insertTeacherForm;
    private final Teacher teacher;
    private final Mode mode;
    private final Communication communication;

    public InsertTeacherController(InsertTeacherForm insertTeacherForm, Teacher teacher, Mode mode) {
        this.insertTeacherForm = insertTeacherForm;
        this.teacher = teacher;
        this.mode = mode;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void openInsertTeacherForm() {
        if (mode == Mode.UPDATE) {
            insertTeacherForm.getTxtFirstName().setText(teacher.getFirstName());
            insertTeacherForm.getTxtLastName().setText(teacher.getLastName());
            insertTeacherForm.setTitle("Azuriraj nastavnika");
        } else {
            insertTeacherForm.setTitle("Unesi nastavnika");
        }
        insertTeacherForm.setLocationRelativeTo(insertTeacherForm.getParent());
        insertTeacherForm.setVisible(true);
    }

    private void closeInsertTeacherForm() {
        insertTeacherForm.dispose();
    }

    private void addActionListeners() {
        insertTeacherForm.saveAddActionListener((ActionEvent e) -> {
            String firstName = insertTeacherForm.getTxtFirstName().getText();
            String lastName = insertTeacherForm.getTxtLastName().getText();
            switch (mode) {
                case INSERT -> {
                    try {
                        Teacher t = new Teacher(null, firstName, lastName);
                        communication.insertTeacher(t);
                        JOptionPane.showMessageDialog(insertTeacherForm, "Sistem je zapamtio nastavnika.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertTeacherForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertTeacherForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        teacher.setFirstName(firstName);
                        teacher.setLastName(lastName);
                        communication.updateTeacher(teacher);
                        JOptionPane.showMessageDialog(insertTeacherForm, "Sistem je zapamtio nastavnika.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertTeacherForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertTeacherForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                default ->
                    throw new AssertionError();
            }
        });
    }

}
