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
import view.panels.*;

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
    private final RegistrationRequestPanel studentOfficerPanel;
    private final StudentPanel studentPanel;
    private final CompanyPanel companyPanel;
    private final ProfilePanel profilePanel;

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
        this.studentOfficerPanel = new RegistrationRequestPanel();
        this.studentPanel = new StudentPanel();
        this.companyPanel = new CompanyPanel();
        this.profilePanel = new ProfilePanel();
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

    public StudentPanel getStudentPanel() {
        return studentPanel;
    }

    public RegistrationRequestPanel getStudentOfficerPanel() {
        return studentOfficerPanel;
    }

    public CompanyPanel getCompanyPanel() {
        return companyPanel;
    }

    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }

    public void openClientForm() {
        clientForm.setVisible(true);
        clientForm.setLocationRelativeTo(null);
        clientForm.getMenuAdministration().setVisible(false);
        clientForm.getMenuInternship().setVisible(false);
        clientForm.getMenuProfile().setVisible(false);
        clientForm.getItemRegistrationRequest().setVisible(false);
        clientForm.getMainPanel().setLayout(cardLayout);
        clientForm.getMainPanel().add(logInPanel.getLogInPanel(), "logInPanel");
        clientForm.getMainPanel().add(internshipPanel, "internshipPanel");
        clientForm.getMainPanel().add(countryPanel, "countriesPanel");
        clientForm.getMainPanel().add(examPeriodPanel, "examPeriodsPanel");
        clientForm.getMainPanel().add(teacherPanel, "teacherPanel");
        clientForm.getMainPanel().add(studyLevelPanel, "studyLevelPanel");
        clientForm.getMainPanel().add(studentPanel, "studentPanel");
        clientForm.getMainPanel().add(studentOfficerPanel, "studentOfficerPanel");
        clientForm.getMainPanel().add(companyPanel, "companyPanel");
        clientForm.getMainPanel().add(profilePanel, "profilePanel");
    }

    private void openLogInPanel() {
        cardLayout.show(clientForm.getMainPanel(), "logInPanel");
        coordinator.openLogInPanel();
    }

    public void openInternshipPanel() {
        clientForm.getMenuAdministration().setVisible(true);
        clientForm.getMenuInternship().setVisible(true);
        clientForm.getMenuProfile().setVisible(true);
        if (coordinator.getStudentOfficer().getAdmin()) {
            clientForm.getItemRegistrationRequest().setVisible(true);
        }
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

    private void openCompanyPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "companyPanel");
            coordinator.openCompanyPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openProfilePanel() {
        cardLayout.show(clientForm.getMainPanel(), "profilePanel");
        coordinator.openProfilePanel();
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
        clientForm.showCompaniesAddActionListener((ActionEvent e) -> {
            openCompanyPanel();
        });
        clientForm.showProfileAddActionListener((ActionEvent e) -> {
            openProfilePanel();
        });
        clientForm.logOutAddActionListener((ActionEvent e) -> {
            openLogInPanel();
        });
    }
}
