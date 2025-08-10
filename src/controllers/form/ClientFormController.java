/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import coordinator.Coordinator;
import view.forms.ClientForm;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.panels.CountriesPanel;
import view.panels.ExamPeriodsPanel;
import view.panels.LogInPanel;
import view.panels.ReportsPanel;
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
    private final ReportsPanel reportsPanel;
    private final CountriesPanel countriesPanel;
    private final ExamPeriodsPanel examPeriodsPanel;
    private final TeacherPanel teacherPanel;
    // svi paneli ovde

    public ClientFormController(ClientForm clientForm) {
        this.coordinator = Coordinator.getInstance();
        this.clientForm = clientForm;
        this.cardLayout = new CardLayout();
        this.logInPanel = new LogInPanel();
        this.reportsPanel = new ReportsPanel();
        this.countriesPanel = new CountriesPanel();
        this.examPeriodsPanel = new ExamPeriodsPanel();
        this.teacherPanel = new TeacherPanel();
        // svi paneli ovde
        addActionListeners();
    }

    public ClientForm getClientForm() {
        return clientForm;
    }

    public LogInPanel getLogInPanel() {
        return logInPanel;
    }

    public ReportsPanel getReportsPanel() {
        return reportsPanel;
    }

    public CountriesPanel getCountriesPanel() {
        return countriesPanel;
    }

    public ExamPeriodsPanel getExamPeriodsPanel() {
        return examPeriodsPanel;
    }

    public TeacherPanel getTeacherPanel() {
        return teacherPanel;
    }

    // svi getteri panela ovde
    public void openClientForm() {
        clientForm.setVisible(true);
        clientForm.setLocationRelativeTo(null);
        clientForm.getMenu().setVisible(false);
        clientForm.getLblPlaceholder().setVisible(false);
        clientForm.getLblStudentOfficer().setVisible(false);
        clientForm.getMainPanel().setLayout(cardLayout);
        clientForm.getMainPanel().add(logInPanel.getLogInPanel(), "logInPanel");
        clientForm.getMainPanel().add(reportsPanel, "reportsPanel");
        clientForm.getMainPanel().add(countriesPanel, "countriesPanel");
        clientForm.getMainPanel().add(examPeriodsPanel, "examPeriodsPanel");
        clientForm.getMainPanel().add(teacherPanel, "teacherPanel");
        // svi paneli ovde
    }

    public void openReportsPanel() {
        clientForm.getMenu().setVisible(true);
        clientForm.getLblPlaceholder().setVisible(true);
        clientForm.getLblStudentOfficer().setVisible(true);
        clientForm.getLblStudentOfficer().setText(Coordinator.studentOfficer.toString());
        // pomeriti ovo u strucnu praksu
        cardLayout.show(clientForm.getMainPanel(), "reportsPanel");
    }

    public void openCountriesPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "countriesPanel");
            coordinator.openCountriesPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openExamPeriodsPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "examPeriodsPanel");
            coordinator.openExamPeriodsPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openTeacherPanel() {
        try {
            cardLayout.show(clientForm.getMainPanel(), "teacherPanel");
            coordinator.openTeacherPanel();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(reportsPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        clientForm.showReportsAddActionListener((ActionEvent e) -> {
            openReportsPanel();
        });
        clientForm.showCountriesAddActionListener((ActionEvent e) -> {
            openCountriesPanel();
        });
        clientForm.showExamPeriodsAddActionListener((ActionEvent e) -> {
            openExamPeriodsPanel();
        });
        clientForm.showTeachersAddActionListener((ActionEvent e) -> {
            openTeacherPanel();
        });
        // za svaku stavku menija, ovde
    }

}
