/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domain.Report;
import java.io.IOException;
import view.FilePickerForm;

/**
 *
 * @author kotar
 */
public class FilePickerFormController {
    
    private final FilePickerForm filePickerForm;

    public FilePickerFormController(FilePickerForm filePickerForm) {
        this.filePickerForm = filePickerForm;
    }

    public Report getSelectedReport() throws IOException, Exception {
        return filePickerForm.getSelectedReport(null);
    }
    
}
