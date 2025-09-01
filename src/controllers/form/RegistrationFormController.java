/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.RegistrationRequest;
import domain.StudyLevel;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import view.forms.RegistrationForm;

/**
 *
 * @author kotar
 */
public class RegistrationFormController {

    private final RegistrationForm registrationForm;
    private final Communication communication;

    public RegistrationFormController(RegistrationForm registerForm) {
        this.registrationForm = registerForm;
        this.communication = Communication.getInstance();
        addActionListeners();
    }
    
    private void prepareForm() throws Exception {
        List<StudyLevel> studyLevels = communication.getAllStudyLevels();
        for (StudyLevel studyLevel : studyLevels) {
            registrationForm.getComboStudyLevel().addItem(studyLevel);
        }
    }

    public void openRegistrationForm() throws Exception {
        prepareForm();
        registrationForm.setLocationRelativeTo(registrationForm.getParent());
        registrationForm.setVisible(true);
    }

    public void closeRegistrationForm() {
        registrationForm.dispose();
    }

    private void addActionListeners() {
        registrationForm.registerAddActionListener((ActionEvent e) -> {
            try {
                //VALIDACIJA OBAVEZNA
                String firstName = registrationForm.getTxtFirstName().getText();
                String lastName = registrationForm.getTxtLastName().getText();
                String email = registrationForm.getTxtEmail().getText();
                boolean admin = registrationForm.getCbAdmin().isSelected();
                String password = String.valueOf(registrationForm.getTxtPassword2().getPassword());
                StudyLevel studyLevel = (StudyLevel) registrationForm.getComboStudyLevel().getSelectedItem();
                RegistrationRequest registrationRequest = new RegistrationRequest(null, firstName, lastName, email, password, admin, studyLevel);
                communication.insertRegistrationRequest(registrationRequest);
                JOptionPane.showMessageDialog(registrationForm, "Sistem je zapamtio zahtev za registraciju.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                closeRegistrationForm();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(registrationForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
