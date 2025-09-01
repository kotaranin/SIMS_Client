/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import coordinator.Coordinator;
import view.forms.ClientForm;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import view.panels.CountryPanel;
import view.panels.ExamPeriodPanel;
import view.panels.HelpPanel;
import view.panels.InternshipPanel;
import view.panels.LogInPanel;
import view.panels.RegistrationRequestPanel;
import view.panels.StudentPanel;
import view.panels.StudyLevelPanel;
import view.panels.TeacherPanel;

/**
 *
 * @author kotar
 */
public class ClientFormController {

    private final Coordinator coordinator;
    private final ClientForm clientForm;
    private final CardLayout cardLayout;
    private final LogInPanel logInPanel;
    private final InternshipPanel internshipPanel;
    private final CountryPanel countryPanel;
    private final ExamPeriodPanel examPeriodPanel;
    private final TeacherPanel teacherPanel;
    private final StudyLevelPanel studyLevelPanel;
    private final HelpPanel helpPanel;
    private final RegistrationRequestPanel studentOfficerPanel;
    private final StudentPanel studentPanel;
    // svi paneli ovde

    public ClientFormController(ClientForm clientForm) {
        this.coordinator = Coordinator.getInstance();
        this.clientForm = clientForm;
        this.cardLayout = new CardLayout();
        this.logInPanel = new LogInPanel();
        this.internshipPanel = new InternshipPanel();
        this.countryPanel = new CountryPanel();
        this.examPeriodPanel = new ExamPeriodPanel();
        this.teacherPanel = new TeacherPanel();
        this.studyLevelPanel = new StudyLevelPanel();
        this.helpPanel = new HelpPanel();
        this.studentOfficerPanel = new RegistrationRequestPanel();
        this.studentPanel = new StudentPanel();
        // svi paneli ovde
        addActionListeners();
    }

    public ClientForm getClientForm() {
        return clientForm;
    }

    public LogInPanel getLogInPanel() {
        return logInPanel;
    }

    public InternshipPanel getInternshipPanel() {
        return internshipPanel;
    }

    public CountryPanel getCountryPanel() {
        return countryPanel;
    }

    public ExamPeriodPanel getExamPeriodPanel() {
        return examPeriodPanel;
    }

    public TeacherPanel getTeacherPanel() {
        return teacherPanel;
    }

    public StudyLevelPanel getStudyLevelPanel() {
        return studyLevelPanel;
    }

    public HelpPanel getHelpPanel() {
        return helpPanel;
    }

    public StudentPanel getStudentPanel() {
        return studentPanel;
    }

    public RegistrationRequestPanel getStudentOfficerPanel() {
        return studentOfficerPanel;
    }

    // svi getteri panela ovde
    public void openClientForm() {
        clientForm.setVisible(true);
        clientForm.setLocationRelativeTo(null);
        clientForm.getMenuAdministration().setVisible(false);
        clientForm.getMenuInternship().setVisible(false);
        clientForm.getMenuAbout().setVisible(false);
        clientForm.getLblPlaceholder().setVisible(false);
        clientForm.getLblStudentOfficer().setVisible(false);
        clientForm.getItemRegistrationRequest().setVisible(false);
        clientForm.getMainPanel().setLayout(cardLayout);
        clientForm.getMainPanel().add(logInPanel.getLogInPanel(), "logInPanel");
        clientForm.getMainPanel().add(internshipPanel, "internshipPanel");
        clientForm.getMainPanel().add(countryPanel, "countriesPanel");
        clientForm.getMainPanel().add(examPeriodPanel, "examPeriodsPanel");
        clientForm.getMainPanel().add(teacherPanel, "teacherPanel");
        clientForm.getMainPanel().add(studyLevelPanel, "studyLevelPanel");
        clientForm.getMainPanel().add(helpPanel, "helpPanel");
        clientForm.getMainPanel().add(studentPanel, "studentPanel");
        clientForm.getMainPanel().add(studentOfficerPanel, "studentOfficerPanel");
        // svi paneli ovde
    }

    public void openInternshipPanel() {
        clientForm.getMenuAdministration().setVisible(true);
        clientForm.getMenuInternship().setVisible(true);
        clientForm.getMenuAbout().setVisible(true);
        clientForm.getLblPlaceholder().setVisible(true);
        clientForm.getLblStudentOfficer().setVisible(true);
        if (coordinator.getStudentOfficer().isAdmin())
            clientForm.getItemRegistrationRequest().setVisible(true);
        clientForm.getLblStudentOfficer().setText(coordinator.getStudentOfficer().toString());
        cardLayout.show(clientForm.getMainPanel(), "internshipPanel");
    }

    private void openCountryPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "countriesPanel");
            coordinator.openCountryPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openExamPeriodPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "examPeriodsPanel");
            coordinator.openExamPeriodPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openTeacherPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "teacherPanel");
            coordinator.openTeacherPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openStudyLevelPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "studyLevelPanel");
            coordinator.openStudyLevelPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegistrationRequestPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "studentOfficerPanel");
            coordinator.openRegistrationRequestPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openStudentPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "studentPanel");
            coordinator.openStudentPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openHelpPanel() {
        cardLayout.show(clientForm.getMainPanel(), "helpPanel");
        coordinator.openHelpPanel();
    }

    private void addActionListeners() {
        clientForm.showInternshipsAddActionListener((ActionEvent e) -> {
            openInternshipPanel();
        });
        clientForm.showCountriesAddActionListener((ActionEvent e) -> {
            openCountryPanel();
        });
        clientForm.showExamPeriodsAddActionListener((ActionEvent e) -> {
            openExamPeriodPanel();
        });
        clientForm.showTeachersAddActionListener((ActionEvent e) -> {
            openTeacherPanel();
        });
        clientForm.showStudyLevelsAddActionListener((ActionEvent e) -> {
            openStudyLevelPanel();
        });
        clientForm.showRegistrationRequestsAddActionListener((ActionEvent e) -> {
            openRegistrationRequestPanel();
        });
        clientForm.showStudentsAddActionListener((ActionEvent e) -> {
            openStudentPanel();
        });
        clientForm.showHelpAddActionListener((ActionEvent e) -> {
            openHelpPanel();
        });
        // za svaku stavku menija, ovde
    }

}
