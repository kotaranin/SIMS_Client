/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.RegistrationRequest;
import java.util.List;

/**
 *
 * @author kotar
 */
public class RegistrationRequestTM extends AbstractTM {

    public RegistrationRequestTM(List list) {
        super(list);
        this.columns = new String[]{"ID", "Ime", "Prezime", "E-mail", "Nivo studija", "Administrator"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        RegistrationRequest registrationRequest = (RegistrationRequest) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return registrationRequest.getIdRegistrationRequest();
            }
            case 1 -> {
                return registrationRequest;
            }
            case 2 -> {
                return registrationRequest.getLastName();
            }
            case 3 -> {
                return registrationRequest.getEmail();
            }
            case 4 -> {
                return registrationRequest.getStudyLevel();
            }
            case 5 -> {
                return (registrationRequest.isAdmin()) ? "DA" : "NE";
            }
            default ->
                throw new AssertionError();
        }
    }

}
