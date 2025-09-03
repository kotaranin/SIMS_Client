/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudentOfficer;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private final Font font;

    public LogInPanelController(LogInPanel logInPanel) {
        this.logInPanel = logInPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.font = logInPanel.getLblForgot().getFont();
        addActionListeners();
    }

    public void preparePanel() {
        logInPanel.getLblForgot().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void addActionListeners() {
        logInPanel.getLblForgot().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logInPanel.getLblForgot().setFont(new Font("Segoe UI", Font.ITALIC, 12));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logInPanel.getLblForgot().setFont(font);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                coordinator.openQuestionForm();
            }

        });
        logInPanel.logInAddActionListener((ActionEvent e) -> {
            try {
                String email = logInPanel.getTxtEmail().getText();
                String password = String.valueOf(logInPanel.getTxtPassword().getPassword());
                StudentOfficer studentOfficer = new StudentOfficer();
                studentOfficer.setEmail(email);
                studentOfficer.setHashedPassword(password);
                studentOfficer = communication.passwordLogIn(studentOfficer);
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
