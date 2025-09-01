/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudentOfficer;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import view.panels.LogInPanel;

/**
 *
 * @author kotar
 */
public class LogInPanelController {

    private final LogInPanel logInPanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public LogInPanelController(LogInPanel logInPanel) {
        this.logInPanel = logInPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    private void addActionListeners() {
        logInPanel.logInAddActionListener((ActionEvent e) -> {
            try {
                String email = logInPanel.getTxtEmail().getText();
                String password = String.valueOf(logInPanel.getTxtPassword().getPassword());
                StudentOfficer studentOfficer = new StudentOfficer();
                studentOfficer.setEmail(email);
                studentOfficer.setPassword(password);
                studentOfficer = communication.logIn(studentOfficer);
                coordinator.setStudentOfficer(studentOfficer);
                coordinator.openInternshipPanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(logInPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        logInPanel.registerAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertRegistrationForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(logInPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
