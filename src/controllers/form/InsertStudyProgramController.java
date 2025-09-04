/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import coordinator.Coordinator;
import domain.StudyProgram;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.ModuleTM;
import view.forms.InsertStudyProgramForm;

/**
 *
 * @author kotar
 */
public class InsertStudyProgramController {

    private final InsertStudyProgramForm insertStudyProgramForm;
    private final StudyProgram studyProgram;
    private final Mode mode;
    private final Coordinator coordinator;

    public InsertStudyProgramController(InsertStudyProgramForm insertStudyProgramForm, StudyProgram studyProgram, Mode mode) {
        this.insertStudyProgramForm = insertStudyProgramForm;
        this.studyProgram = studyProgram;
        this.mode = mode;
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillModules(List<domain.Module> modules) {
        insertStudyProgramForm.getTblModule().setModel(new ModuleTM(modules));
    }

    public void openInsertStudyProgramForm() throws Exception {
        if (mode == Mode.UPDATE) {
            insertStudyProgramForm.getTxtStudyProgramName().setText(studyProgram.getName());
            fillModules(studyProgram.getModules());
            insertStudyProgramForm.setTitle("Azuriraj studijski program");
        } else {
            fillModules(new LinkedList<>());
            insertStudyProgramForm.setTitle("Kreiraj studijski program");
        }
        insertStudyProgramForm.setLocationRelativeTo(insertStudyProgramForm.getParent());
        insertStudyProgramForm.setVisible(true);
    }

    public void closeInsertStudyProgramForm() {
        insertStudyProgramForm.dispose();
    }

    public void insert(domain.Module module) {
        ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).insert(module);
    }

    public void update(domain.Module module) {
        ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).update(module);
    }

    private void addActionListeners() {
        insertStudyProgramForm.getTblModule().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = insertStudyProgramForm.getTblModule().getSelectedRow() != -1;
                insertStudyProgramForm.getBtnUpdateModule().setEnabled(selected);
            }
        });
        insertStudyProgramForm.insertModuleAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertModuleForm(null, Mode.INSERT);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyProgramForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudyProgramForm.updateModuleAddActionListener((ActionEvent e) -> {
            try {
                int row = insertStudyProgramForm.getTblModule().getSelectedRow();
                domain.Module module = (domain.Module) ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).getValueAt(row, 1);
                coordinator.openInsertModuleForm(module, Mode.UPDATE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyProgramForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudyProgramForm.saveAddActionListener((ActionEvent e) -> {
            StudyProgram sp = new StudyProgram();
            sp.setName(insertStudyProgramForm.getTxtStudyProgramName().getText());
            sp.setModules(((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).getList());
            if (mode == Mode.INSERT) {
                coordinator.getInsertStudyLevelController().insert(sp);
            } else {
                sp.setIdStudyProgram(studyProgram.getIdStudyProgram());
                coordinator.getInsertStudyLevelController().update(sp);
            }
            closeInsertStudyProgramForm();
        });
    }

}
