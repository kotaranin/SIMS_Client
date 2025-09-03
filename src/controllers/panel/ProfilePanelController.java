/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.StudentOfficer;
import enums.Mode;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import view.panels.ProfilePanel;

/**
 *
 * @author kotar
 */
public class ProfilePanelController {

    private final ProfilePanel profilePanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public ProfilePanelController(ProfilePanel profilePanel) {
        this.profilePanel = profilePanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void preparePanel() {
        StudentOfficer studentOfficer = coordinator.getStudentOfficer();
        profilePanel.getTxtFirstName().setText(studentOfficer.getFirstName());
        profilePanel.getTxtLastName().setText(studentOfficer.getLastName());
        profilePanel.getTxtStudyLevel().setText(studentOfficer.getStudyLevel().toString());
        profilePanel.getCbAdmin().setSelected(studentOfficer.isAdmin());
        profilePanel.getTxtEmail().setText(studentOfficer.getEmail());
        profilePanel.getTxtPassword().setText(studentOfficer.getHashedPassword());
        profilePanel.getTxtPassword().getInputMap().put(KeyStroke.getKeyStroke("ctrl C"), "none");
        profilePanel.getTxtPassword().getInputMap().put(KeyStroke.getKeyStroke("ctrl X"), "none");
    }

    private void addActionListeners() {
        profilePanel.updatePasswordAddActionListener((ActionEvent e) -> {
            coordinator.openNewPasswordForm(Mode.CHANGE);
            preparePanel();
        });
    }
}
