/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import coordinator.Coordinator;
import domain.Company;
import domain.ExamPeriod;
import domain.Internship;
import domain.Report;
import domain.Student;
import domain.Teacher;
import enums.Grade;
import enums.Mode;
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import validators.InternshipValidator;
import view.forms.InsertInternshipForm;

/**
 *
 * @author kotar
 */
public class InsertInternshipController {

    private final InsertInternshipForm insertInternshipForm;
    private final Communication communication;
    private final Coordinator coordinator;
    private final Internship internship;
    private final Mode mode;
    private Report report;
    private final Font font;

    public InsertInternshipController(InsertInternshipForm insertInternshipForm, Internship internship, Mode mode) {
        this.insertInternshipForm = insertInternshipForm;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.internship = internship;
        this.mode = mode;
        this.font = insertInternshipForm.getLblReport().getFont();
        addActionListeners();
    }

    private void prepareForm() throws Exception {
        List<Student> students = communication.getAllStudents();
        List<Company> companies = communication.getAllCompanies();
        List<Teacher> teachers = communication.getAllTeachers();
        List<ExamPeriod> examPeriods = communication.getAllExamPeriods();
        for (Student student : students) {
            insertInternshipForm.getComboStudent().addItem(student);
        }
        for (Company company : companies) {
            insertInternshipForm.getComboCompany().addItem(company);
        }
        insertInternshipForm.getComboGrade().addItem(Grade.POLOŽIO);
        insertInternshipForm.getComboGrade().addItem(Grade.NIJE_POLOŽIO);
        for (Teacher teacher : teachers) {
            insertInternshipForm.getComboTeacher().addItem(teacher);
        }
        for (ExamPeriod examPeriod : examPeriods) {
            insertInternshipForm.getComboExamPeriod().addItem(examPeriod);
        }
    }

    public void openInsertInternshipForm() throws Exception {
        prepareForm();
        if (mode == Mode.UPDATE) {
            this.report = internship.getReport();
            insertInternshipForm.getComboStudent().getModel().setSelectedItem(internship.getStudent());
            insertInternshipForm.getComboCompany().getModel().setSelectedItem(internship.getCompany());
            insertInternshipForm.getTxtStartDate().setText(internship.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            insertInternshipForm.getTxtEndDate().setText(internship.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            insertInternshipForm.getLblReport().setText(internship.getReport().toString());
            insertInternshipForm.getTxtDefenseDate().setText(internship.getDefenseDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            insertInternshipForm.getComboGrade().getModel().setSelectedItem(internship.getGrade());
            insertInternshipForm.getComboTeacher().getModel().setSelectedItem(internship.getTeacher());
            insertInternshipForm.getComboExamPeriod().getModel().setSelectedItem(internship.getExamPeriod());
            insertInternshipForm.setTitle("Ažuriraj stručnu praksu");
        } else {
            insertInternshipForm.setTitle("Dodaj stručnu praksu");
        }
        insertInternshipForm.setLocationRelativeTo(insertInternshipForm.getParent());
        insertInternshipForm.setVisible(true);
    }

    public void closeInsertInternshipForm() {
        insertInternshipForm.dispose();
    }

    private void addActionListeners() {
        insertInternshipForm.openReportAddActionListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (report != null) {
                    try {
                        File file = File.createTempFile("doc_", "_" + report.getFileName());
                        file.deleteOnExit();
                        FileOutputStream out = new FileOutputStream(file);
                        out.write(report.getFileContent());
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(insertInternshipForm, "Došlo je do greške pri učitavanju dnevnika prakse.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (report != null) {
                    insertInternshipForm.getLblReport().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    insertInternshipForm.getLblReport().setFont(new Font("Segoe UI", Font.ITALIC, 12));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (report != null) {
                    insertInternshipForm.getLblReport().setFont(font);
                }
            }

        });
        insertInternshipForm.attachReportAddActionListener((ActionEvent e) -> {
            try {
                Report temp = coordinator.openFilePickerForm();
                if (report == null) {
                    report = temp;
                }
                report.setFileName(temp.getFileName());
                report.setFileContent(temp.getFileContent());
                insertInternshipForm.getLblReport().setText(report.getFileName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertInternshipForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertInternshipForm.saveAddActionListener((ActionEvent e) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                Student student = (Student) insertInternshipForm.getComboStudent().getSelectedItem();
                Company company = (Company) insertInternshipForm.getComboCompany().getSelectedItem();
                LocalDate startDate = LocalDate.parse(insertInternshipForm.getTxtStartDate().getText(), formatter);
                LocalDate endDate = LocalDate.parse(insertInternshipForm.getTxtEndDate().getText(), formatter);
                LocalDate defenseDate = LocalDate.parse(insertInternshipForm.getTxtDefenseDate().getText(), formatter);
                Grade grade = (Grade) insertInternshipForm.getComboGrade().getSelectedItem();
                Teacher teacher = (Teacher) insertInternshipForm.getComboTeacher().getSelectedItem();
                ExamPeriod examPeriod = (ExamPeriod) insertInternshipForm.getComboExamPeriod().getSelectedItem();
                switch (mode) {
                    case INSERT -> {
                        Internship i = new Internship(null, startDate, endDate, defenseDate, grade, teacher, examPeriod, report, coordinator.getStudentOfficer(), company, student);
                        new InternshipValidator().checkElementaryContraints(i);
                        communication.insertInternship(i);
                        JOptionPane.showMessageDialog(insertInternshipForm, "Sistem je zapamtio dnevnik prakse.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(insertInternshipForm, "Sistem je zapamtio stručnu praksu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertInternshipForm();
                    }
                    case UPDATE -> {
                        internship.setStudent(student);
                        internship.setCompany(company);
                        internship.setStartDate(startDate);
                        internship.setEndDate(endDate);
                        internship.setReport(report);
                        internship.setDefenseDate(defenseDate);
                        internship.setGrade(grade);
                        internship.setTeacher(teacher);
                        internship.setExamPeriod(examPeriod);
                        new InternshipValidator().checkElementaryContraints(internship);
                        communication.updateInternship(internship);
                        JOptionPane.showMessageDialog(insertInternshipForm, "Sistem je zapamtio dnevnik prakse.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(insertInternshipForm, "Sistem je zapamtio stručnu praksu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertInternshipForm();
                    }
                    default ->
                        throw new AssertionError();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(insertInternshipForm, "Datum mora biti u formatu DD.MM.GGGG.", "Greška", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertInternshipForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
