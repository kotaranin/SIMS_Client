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
import java.util.List;
import javax.swing.JOptionPane;
import view.forms.QuestionForm;

/**
 *
 * @author kotar
 */
public class QuestionFormController {

    private final QuestionForm questionForm;
    private final Communication communication;
    private final Coordinator coordinator;

    public QuestionFormController(QuestionForm questionForm) {
        this.questionForm = questionForm;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void openQuestionForm() {
        questionForm.setLocationRelativeTo(questionForm.getParent());
        questionForm.setVisible(true);
    }

    private void closeQuestionForm() {
        questionForm.dispose();
    }

    private void addActionListeners() {
        questionForm.findUserAddActionListener((ActionEvent e) -> {
            try {
                List<StudentOfficer> studentOfficers = communication.getAllStudentOfficers();
                String email = questionForm.getTxtEmail().getText();
                for (StudentOfficer studentOfficer : studentOfficers) {
                    if (studentOfficer.getEmail().equals(email)) {
                        coordinator.setStudentOfficer(studentOfficer);
                        questionForm.getTxtQuestion().setText(studentOfficer.getQuestion());
                        questionForm.getTxtAnswer().setEditable(true);
                        questionForm.getBtnOK().setEnabled(true);
                        return;
                    }
                }
                throw new Exception("Sistem ne moze da nadje sluzbenika.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(questionForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        questionForm.checkAddActionListener((ActionEvent e) -> {
            try {
                String answer = String.valueOf(questionForm.getTxtAnswer().getPassword());
                coordinator.getStudentOfficer().setAnswerSalt(null);
                coordinator.getStudentOfficer().setHashedAnswer(answer);
                coordinator.setStudentOfficer(communication.questionLogIn(coordinator.getStudentOfficer()));
                coordinator.openNewPasswordForm(Mode.FORGOT);
                closeQuestionForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(questionForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
