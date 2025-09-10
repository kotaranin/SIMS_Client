/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.Teacher;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.TeacherTM;
import view.panels.TeacherPanel;

/**
 *
 * @author kotar
 */
public class TeacherPanelController {

    private final TeacherPanel teacherPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private boolean initialized;

    public TeacherPanelController(TeacherPanel teacherPanel) {
        this.teacherPanel = teacherPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        initialized = false;
        teacherPanel.getTxtFirstName().setText(null);
        teacherPanel.getTxtLastName().setText(null);
        fillTeachers(communication.getAllTeachers());
        initialized = true;
    }

    private void fillTeachers(List<Teacher> teachers) {
        teacherPanel.getTblTeachers().setModel(new TeacherTM(teachers));
    }

    private void addActionListeners() {
        teacherPanel.getTblTeachers().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = teacherPanel.getTblTeachers().getSelectedRow() != -1;
                teacherPanel.getBtnUpdate().setEnabled(selected);
            }
        });
        teacherPanel.getTxtFirstName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        teacherPanel.getTxtLastName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        teacherPanel.insertAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertTeacherForm(null, Mode.INSERT);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        teacherPanel.updateAddActionListener((ActionEvent e) -> {
            try {
                int row = teacherPanel.getTblTeachers().getSelectedRow();
                Teacher teacher = (Teacher) ((TeacherTM) teacherPanel.getTblTeachers().getModel()).getValueAt(row, 0);
                coordinator.openInsertTeacherForm(teacher, Mode.UPDATE);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void search() {
        try {
            String firstName = teacherPanel.getTxtFirstName().getText().trim();
            String lastName = teacherPanel.getTxtLastName().getText().trim();
            Teacher teacher = new Teacher(null, firstName, lastName);
            List<Teacher> teachers = communication.searchTeachers(teacher);
            fillTeachers(teachers);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
;

}
