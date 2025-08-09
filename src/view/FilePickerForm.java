/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import domain.Report;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author kotar
 */
public class FilePickerForm {

    public Report getSelectedReport(ReportsPanel reportsPanel) throws IOException, Exception {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Izaberite datoteku:");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Dozvoljeni tipovi su .doc, .docx ili .pdf", "doc", "docx", "pdf"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(reportsPanel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName();
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return new Report(null, fileName, fileContent);
        }
        throw new Exception("Niste izabrali datoteku!");
    }
}
