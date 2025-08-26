/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import coordinator.Coordinator;
import domain.Module;
import enums.Mode;
import java.awt.event.ActionEvent;
import view.forms.InsertModuleForm;

/**
 *
 * @author kotar
 */
public class InsertModuleController {
    
    private final InsertModuleForm insertModuleForm;
    private final domain.Module module;
    private final Mode mode;
    private final Coordinator coordinator;
    
    public InsertModuleController(InsertModuleForm insertModuleForm, Module module, Mode mode) {
        this.insertModuleForm = insertModuleForm;
        this.module = module;
        this.mode = mode;
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }
    
    public void openInsertModuleForm() {
        if (mode == Mode.UPDATE) {
            insertModuleForm.getTxtModuleName().setText(module.getName());
            insertModuleForm.setTitle("Azuriraj modul");
        } else {
            insertModuleForm.setTitle("Kreiraj modul");
        }
        insertModuleForm.setLocationRelativeTo(insertModuleForm.getParent());
        insertModuleForm.setVisible(true);
    }
    
    private void closeInsertModuleForm() {
        insertModuleForm.dispose();
    }
    
    private void addActionListeners() {
        insertModuleForm.saveAddActionListener((ActionEvent e) -> {
            domain.Module m = new Module();
            m.setName(insertModuleForm.getTxtModuleName().getText());
            if (mode == Mode.INSERT) {
                coordinator.getInsertStudyProgramController().insert(m);
            } else {
                m.setIdModule(module.getIdModule());
                coordinator.getInsertStudyProgramController().update(m);
            }
            closeInsertModuleForm();
        });
    }
    
}
