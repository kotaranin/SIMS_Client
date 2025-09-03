/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudentOfficer;
import enums.Mode;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import view.forms.NewPasswordForm;

/**
 *
 * @author kotar
 */
public class NewPasswordController {

    private final NewPasswordForm newPasswordForm;
    private final Mode mode;
    private final Communication communication;
    private StudentOfficer studentOfficer;
    private final Coordinator coordinator;

    public NewPasswordController(NewPasswordForm newPasswordForm, Mode mode) {
        this.newPasswordForm = newPasswordForm;
        this.mode = mode;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.studentOfficer = coordinator.getStudentOfficer();
        addActionListeners();
    }

    private void prepareForm() {
        if (mode == Mode.FORGOT) {
            newPasswordForm.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            newPasswordForm.getLblOldPassword().setVisible(false);
            newPasswordForm.getTxtOldPassword().setVisible(false);
        }
        newPasswordForm.getTxtOldPassword().getInputMap().put(KeyStroke.getKeyStroke("ctrl C"), "none");
        newPasswordForm.getTxtOldPassword().getInputMap().put(KeyStroke.getKeyStroke("ctrl X"), "none");
        newPasswordForm.getTxtNewPassword1().getInputMap().put(KeyStroke.getKeyStroke("ctrl C"), "none");
        newPasswordForm.getTxtNewPassword1().getInputMap().put(KeyStroke.getKeyStroke("ctrl X"), "none");
        newPasswordForm.getTxtNewPassword2().getInputMap().put(KeyStroke.getKeyStroke("ctrl C"), "none");
        newPasswordForm.getTxtNewPassword2().getInputMap().put(KeyStroke.getKeyStroke("ctrl X"), "none");
    }

    public void openNewPasswordForm() {
        prepareForm();
        newPasswordForm.setLocationRelativeTo(newPasswordForm.getParent());
        newPasswordForm.setVisible(true);
    }

    private void closeNewPasswordForm() {
        newPasswordForm.dispose();
    }

    private void addActionListeners() {
        newPasswordForm.updatePasswordAddActionListener((ActionEvent e) -> {
            // VALIDADIJA OBAVEZNA
            String oldPassword = String.valueOf(newPasswordForm.getTxtOldPassword().getPassword());
            String newPassword = String.valueOf(newPasswordForm.getTxtNewPassword2().getPassword());
            StudentOfficer so = new StudentOfficer();
            so.setEmail(studentOfficer.getEmail());
            so.setHashedPassword(oldPassword);
            switch (mode) {
                case CHANGE -> {
                    try {
                        so = communication.passwordLogIn(so);
                        so.setPasswordSalt(null);
                        so.setHashedPassword(newPassword);
                        communication.updateStudentOfficer(so);
                        studentOfficer = so;
                        JOptionPane.showMessageDialog(newPasswordForm, "Sistem je zapamtio sluzbenika.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeNewPasswordForm();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(newPasswordForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case FORGOT -> {
                    try {
                        studentOfficer.setPasswordSalt(null);
                        studentOfficer.setHashedPassword(newPassword);
                        communication.updateStudentOfficer(studentOfficer);
                        JOptionPane.showMessageDialog(newPasswordForm, "Sistem je zapamtio sluzbenika.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeNewPasswordForm();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(newPasswordForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                default ->
                    throw new AssertionError();
            }
        });
    }

}
