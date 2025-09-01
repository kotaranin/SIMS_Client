/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.Company;
import domain.ExamPeriod;
import domain.Internship;
import domain.Student;
import domain.Teacher;
import enums.Grade;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import table_models.InternshipTM;
import view.panels.InternshipPanel;

/**
 *
 * @author kotar
 */
public class InternshipPanelController {

    private final InternshipPanel internshipPanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public InternshipPanelController(InternshipPanel internshipPanel) {
        this.internshipPanel = internshipPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        List<Student> students = communication.getAllStudents();
        List<Company> companies = communication.getAllCompanies();
        List<Teacher> teachers = communication.getAllTeachers();
        List<ExamPeriod> examPeriods = communication.getAllExamPeriods();
        for (Student student : students) {
            internshipPanel.getComboStudent().addItem(student);
        }
        for (Company company : companies) {
            internshipPanel.getComboCompany().addItem(company);
        }
        for (Grade value : Grade.values()) {
            internshipPanel.getComboGrade().addItem(value);
        }
        for (Teacher teacher : teachers) {
            internshipPanel.getComboTeacher().addItem(teacher);
        }
        for (ExamPeriod examPeriod : examPeriods) {
            internshipPanel.getComboExamPeriod().addItem(examPeriod);
        }
        fillInternships(communication.getAllInternships());
    }

    public void fillInternships(List<Internship> internships) {
        internshipPanel.getTblInternship().setModel(new InternshipTM(internships));
    }

    private void addActionListeners() {
        internshipPanel.getTblInternship().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = internshipPanel.getTblInternship().getSelectedRow() != -1;
                internshipPanel.getBtnDeleteInternship().setEnabled(selected);
                internshipPanel.getBtnUpdateInternship().setEnabled(selected);
            }
        });
        internshipPanel.insertInternshipAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openinsertInternshipForm(null, Mode.INSERT);
                fillInternships(communication.getAllInternships());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        internshipPanel.updateInternshipAddActionListener((ActionEvent e) -> {
            try {
                int row = internshipPanel.getTblInternship().getSelectedRow();
                Internship internship = (Internship) ((InternshipTM) internshipPanel.getTblInternship().getModel()).getValueAt(row, 1);
                coordinator.openinsertInternshipForm(internship, Mode.UPDATE);
                fillInternships(communication.getAllInternships());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        internshipPanel.deleteInternshipAddActionListener((ActionEvent e) -> {
            try {
                int row = internshipPanel.getTblInternship().getSelectedRow();
                Internship internship = (Internship) ((InternshipTM) internshipPanel.getTblInternship().getModel()).getValueAt(row, 1);
                communication.deleteInternship(internship);
                JOptionPane.showMessageDialog(internshipPanel, "Sistem je obrisao strucnu praksu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillInternships(communication.getAllInternships());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
