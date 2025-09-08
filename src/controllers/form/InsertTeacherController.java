/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.Teacher;
import enums.Mode;
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import validators.TeacherValidator;
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
            insertTeacherForm.setTitle("Ažuriraj nastavnika");
        } else {
            insertTeacherForm.setTitle("Dodaj nastavnika");
        }
        insertTeacherForm.setLocationRelativeTo(insertTeacherForm.getParent());
        insertTeacherForm.setVisible(true);
    }

    private void closeInsertTeacherForm() {
        insertTeacherForm.dispose();
    }

    private void addActionListeners() {
        insertTeacherForm.saveAddActionListener((ActionEvent e) -> {
            try {
                String firstName = insertTeacherForm.getTxtFirstName().getText();
                String lastName = insertTeacherForm.getTxtLastName().getText();
                switch (mode) {
                    case INSERT -> {
                        Teacher t = new Teacher(null, firstName, lastName);
                        new TeacherValidator().checkElementaryContraints(t);
                        communication.insertTeacher(t);
                        JOptionPane.showMessageDialog(insertTeacherForm, "Sistem je zapamtio nastavnika.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertTeacherForm();
                    }
                    case UPDATE -> {
                        teacher.setFirstName(firstName);
                        teacher.setLastName(lastName);
                        new TeacherValidator().checkElementaryContraints(teacher);
                        communication.updateTeacher(teacher);
                        JOptionPane.showMessageDialog(insertTeacherForm, "Sistem je zapamtio nastavnika.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertTeacherForm();
                    }
                    default ->
                        throw new AssertionError();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertTeacherForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
