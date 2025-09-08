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
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private boolean initialized;

    public RegistrationRequestPanelController(RegistrationRequestPanel registrationRequestPanel) {
        this.registrationRequestPanel = registrationRequestPanel;
        this.communication = Communication.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        List<StudyLevel> studyLevels = communication.getAllStudyLevels();
        List<RegistrationRequest> registrationRequests = communication.getAllRegistrationRequests();
        List<StudentOfficer> studentOfficers = communication.getAllStudentOfficers();
        registrationRequestPanel.getComboStudyLevel().removeAllItems();
        registrationRequestPanel.getComboStudyLevel().addItem(new StudyLevel(null, "Svi nivoi studija", null));
        for (StudyLevel studyLevel : studyLevels) {
            registrationRequestPanel.getComboStudyLevel().addItem(studyLevel);
        }
        initialized = true;
        fillRegistrationRequests(registrationRequests);
        fillStudentOfficers(studentOfficers);
    }

    private void fillRegistrationRequests(List<RegistrationRequest> registrationRequests) {
        registrationRequestPanel.getTblRegistrationRequest().setModel(new RegistrationRequestTM(registrationRequests));
    }

    private void fillStudentOfficers(List<StudentOfficer> studentOfficers) {
        registrationRequestPanel.getTblStudentOfficer().setModel(new StudentOfficerTM(studentOfficers));
    }

    private void search() {
        try {
            String firstName = registrationRequestPanel.getTxtFirstName().getText().trim();
            String lastName = registrationRequestPanel.getTxtLastName().getText().trim();
            String email = registrationRequestPanel.getTxtEmail().getText().trim();
            StudyLevel studyLevel = (StudyLevel) registrationRequestPanel.getComboStudyLevel().getModel().getSelectedItem();
            RegistrationRequest registrationRequest = new RegistrationRequest(null, firstName, lastName, email, null, null, null, null, null, null, studyLevel);
            fillRegistrationRequests(communication.searchRegistrationRequests(registrationRequest));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(registrationRequestPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        registrationRequestPanel.getTblRegistrationRequest().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = registrationRequestPanel.getTblRegistrationRequest().getSelectedRow() != -1;
                registrationRequestPanel.getBtnAccept().setEnabled(selected);
                registrationRequestPanel.getBtnReject().setEnabled(selected);
            }
        });
        registrationRequestPanel.getTxtFirstName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        registrationRequestPanel.getTxtLastName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        registrationRequestPanel.getTxtEmail().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        registrationRequestPanel.getComboStudyLevel().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
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
                studentOfficer.setPasswordSalt(registrationRequest.getPasswordSalt());
                studentOfficer.setHashedPassword(registrationRequest.getHashedPassword());
                studentOfficer.setQuestion(registrationRequest.getQuestion());
                studentOfficer.setAnswerSalt(registrationRequest.getAnswerSalt());
                studentOfficer.setHashedAnswer(registrationRequest.getHashedAnswer());
                if (registrationRequest.getAdmin()) {
                    int choice = JOptionPane.showConfirmDialog(registrationRequestPanel, "Da li dozvoljavate administratorske privilegije korisniku: " + registrationRequest + "?", "Upozorenje", JOptionPane.YES_NO_OPTION);
                    studentOfficer.setAdmin(choice == JOptionPane.YES_OPTION);
                } else {
                    studentOfficer.setAdmin(false);
                }
                studentOfficer.setStudyLevel(registrationRequest.getStudyLevel());
                communication.deleteRegistrationRequest(registrationRequest);
                JOptionPane.showMessageDialog(registrationRequestPanel, "Sistem je obrisao zahtev za registraciju.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                communication.insertStudentOfficer(studentOfficer);
                JOptionPane.showMessageDialog(registrationRequestPanel, "Sistem je zapamtio službenika.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                fillRegistrationRequests(communication.getAllRegistrationRequests());
                fillStudentOfficers(communication.getAllStudentOfficers());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(registrationRequestPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        registrationRequestPanel.rejectAddActionListener((ActionEvent e) -> {
            try {
                int row = registrationRequestPanel.getTblRegistrationRequest().getSelectedRow();
                RegistrationRequest registrationRequest = (RegistrationRequest) ((RegistrationRequestTM) registrationRequestPanel.getTblRegistrationRequest().getModel()).getValueAt(row, 1);
                communication.deleteRegistrationRequest(registrationRequest);
                JOptionPane.showMessageDialog(registrationRequestPanel, "Sistem je obrisao zahtev za registraciju.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                fillRegistrationRequests(communication.getAllRegistrationRequests());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(registrationRequestPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
