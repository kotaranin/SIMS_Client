/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.ExamPeriod;
import enums.Mode;
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import validators.ExamPeriodValidator;
import view.forms.InsertExamPeriodForm;

/**
 *
 * @author kotar
 */
public class InsertExamPeriodController {

    private final InsertExamPeriodForm insertExamPeriodForm;
    private final ExamPeriod examPeriod;
    private final Mode mode;
    private final Communication communication;

    public InsertExamPeriodController(InsertExamPeriodForm insertExamPeriodForm, ExamPeriod examPeriod, Mode mode) {
        this.insertExamPeriodForm = insertExamPeriodForm;
        this.examPeriod = examPeriod;
        this.mode = mode;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void openInsertExamPeriodForm() {
        if (mode == Mode.UPDATE) {
            insertExamPeriodForm.getTxtName().setText(examPeriod.getName());
            insertExamPeriodForm.getTxtStartDate().setText(examPeriod.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            insertExamPeriodForm.getTxtEndDate().setText(examPeriod.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            insertExamPeriodForm.setTitle("Ažuriraj ispitni rok");
        } else {
            insertExamPeriodForm.setTitle("Dodaj ispitni rok");
        }
        insertExamPeriodForm.setLocationRelativeTo(insertExamPeriodForm.getParent());
        insertExamPeriodForm.setVisible(true);
    }

    private void addActionListeners() {
        insertExamPeriodForm.insertExamPeriodAddActionListener((ActionEvent e) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                String name = insertExamPeriodForm.getTxtName().getText();
                LocalDate startdDate = LocalDate.parse(insertExamPeriodForm.getTxtStartDate().getText(), formatter);
                LocalDate endDate = LocalDate.parse(insertExamPeriodForm.getTxtEndDate().getText(), formatter);
                switch (mode) {
                    case INSERT -> {
                        ExamPeriod ep = new ExamPeriod(null, name, startdDate, endDate);
                        new ExamPeriodValidator().checkElementaryContraints(ep);
                        communication.insertExamPeriod(ep);
                        JOptionPane.showMessageDialog(insertExamPeriodForm, "Sistem je zapamtio ispitni rok.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertExamPeriodForm();
                    }
                    case UPDATE -> {
                        examPeriod.setName(name);
                        examPeriod.setStartDate(startdDate);
                        examPeriod.setEndDate(endDate);
                        new ExamPeriodValidator().checkElementaryContraints(examPeriod);
                        communication.updateExamPeriod(examPeriod);
                        JOptionPane.showMessageDialog(insertExamPeriodForm, "Sistem je zapamtio ispitni rok.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertExamPeriodForm();
                    }
                    default ->
                        throw new AssertionError();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(insertExamPeriodForm, "Datum mora biti u formatu DD.MM.GGGG.", "Greška", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertExamPeriodForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void closeInsertExamPeriodForm() {
        insertExamPeriodForm.dispose();
    }

}
