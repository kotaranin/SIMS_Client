/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client_controller;

import coordinator.Coordinator;
import view.ClientForm;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.LogInPanel;
import view.ReportsPanel;

/**
 *
 * @author kotar
 */
public class ClientFormController {

    private final ClientForm clientForm;
    private final CardLayout cardLayout;
    private final LogInPanel logInPanel;
    private final ReportsPanel reportsPanel;
    // svi paneli ovde

    public ClientFormController(ClientForm clientForm) {
        this.clientForm = clientForm;
        this.cardLayout = new CardLayout();
        this.logInPanel = new LogInPanel();
        this.reportsPanel = new ReportsPanel();
        // svi paneli ovde
        addActionListeners();
    }

    public LogInPanel getLogInPanel() {
        return logInPanel;
    }

    public ReportsPanel getReportsPanel() {
        return reportsPanel;
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
        // svi paneli ovde
    }

    public void openReportsPanel() {
        clientForm.getMenu().setVisible(true);
        clientForm.getLblPlaceholder().setVisible(true);
        clientForm.getLblStudentOfficer().setVisible(true);
        clientForm.getLblStudentOfficer().setText(Coordinator.studentOfficer.toString());
        // pomeriti ovo iznad da bude za strucnu praksu
        cardLayout.show(clientForm.getMainPanel(), "reportsPanel");
    }

    public void openCountriesPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void openExamPeriodsPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void openTeacherPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void addActionListeners() {
        clientForm.showReportsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReportsPanel();
            }
        });
        clientForm.showCountriesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCountriesPanel();
            }
        });
        clientForm.showExamPeriodsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openExamPeriodsPanel();
            }
        });
        clientForm.showTeachersAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTeacherPanel();
            }
        });
        // za svaku stavku menija, ovde
    }

}
