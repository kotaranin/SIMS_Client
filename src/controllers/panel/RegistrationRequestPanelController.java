/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import domain.RegistrationRequest;
import domain.StudentOfficer;
import domain.StudyLevel;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import table_models.RegistrationRequestTM;
import table_models.StudentOfficerTM;
import view.panels.RegistrationRequestPanel;

/**
 *
 * @author kotar
 */
public class RegistrationRequestPanelController {

    private final RegistrationRequestPanel registrationRequestPanel;
    private final Communication communication;

    public RegistrationRequestPanelController(RegistrationRequestPanel registrationRequestPanel) {
        this.registrationRequestPanel = registrationRequestPanel;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        List<StudyLevel> studyLevels = communication.getAllStudyLevels();
        List<RegistrationRequest> registrationRequests = communication.getAllRegistrationRequests();
        List<StudentOfficer> studentOfficers = communication.getAllStudentOfficers();
        registrationRequestPanel.getComboStudyLevel().removeAllItems();
        for (StudyLevel studyLevel : studyLevels) {
            registrationRequestPanel.getComboStudyLevel().addItem(studyLevel);
        }
        fillRegistrationRequests(registrationRequests);
        fillStudentOfficers(studentOfficers);
    }

    private void fillRegistrationRequests(List<RegistrationRequest> registrationRequests) {
        registrationRequestPanel.getTblRegistrationRequest().setModel(new RegistrationRequestTM(registrationRequests));
    }

    private void fillStudentOfficers(List<StudentOfficer> studentOfficers) {
        registrationRequestPanel.getTblStudentOfficer().setModel(new StudentOfficerTM(studentOfficers));
    }

    private void addActionListeners() {
        registrationRequestPanel.getTblRegistrationRequest().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = registrationRequestPanel.getTblRegistrationRequest().getSelectedRow() != -1;
                registrationRequestPanel.getBtnAccept().setEnabled(selected);
                registrationRequestPanel.getBtnReject().setEnabled(selected);
            }
        });
        registrationRequestPanel.acceptAddActionListener((ActionEvent e) -> {
            try {
                int row = registrationRequestPanel.getTblRegistrationRequest().getSelectedRow();
                RegistrationRequest registrationRequest = (RegistrationRequest) ((RegistrationRequestTM) registrationRequestPanel.getTblRegistrationRequest().getModel()).getValueAt(row, 1);
                StudentOfficer studentOfficer = new StudentOfficer();
                studentOfficer.setFirstName(registrationRequest.getFirstName());
                studentOfficer.setLastName(registrationRequest.getLastName());
                studentOfficer.setEmail(registrationRequest.getEmail());
                studentOfficer.setPassword(registrationRequest.getPassword());
                if (registrationRequest.isAdmin()) {
                    int choice = JOptionPane.showConfirmDialog(registrationRequestPanel, "Da li dozvoljavate administratorske privilegije korisniku: " + registrationRequest, "Upozorenje", JOptionPane.YES_NO_OPTION);
                    studentOfficer.setAdmin(choice == JOptionPane.YES_OPTION);
                } else {
                    studentOfficer.setAdmin(false);
                }
                studentOfficer.setStudyLevel(registrationRequest.getStudyLevel());
                communication.deleteRegistrationRequest(registrationRequest);
                communication.insertStudentOfficer(studentOfficer);
                fillRegistrationRequests(communication.getAllRegistrationRequests());
                fillStudentOfficers(communication.getAllStudentOfficers());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(registrationRequestPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        registrationRequestPanel.rejectAddActionListener((ActionEvent e) -> {
            try {
                int row = registrationRequestPanel.getTblRegistrationRequest().getSelectedRow();
                RegistrationRequest registrationRequest = (RegistrationRequest) ((RegistrationRequestTM) registrationRequestPanel.getTblRegistrationRequest().getModel()).getValueAt(row, 1);
                communication.deleteRegistrationRequest(registrationRequest);
                fillRegistrationRequests(communication.getAllRegistrationRequests());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(registrationRequestPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
