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

    public RegistrationRequestTM(List<RegistrationRequest> list) {
        super(list);
        this.columns = new String[]{"Ime i prezime", "E-mail", "Nivo studija", "Administrator"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        RegistrationRequest registrationRequest = (RegistrationRequest) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return registrationRequest;
            }
            case 1 -> {
                return registrationRequest.getEmail();
            }
            case 2 -> {
                return registrationRequest.getStudyLevel();
            }
            case 3 -> {
                return (registrationRequest.getAdmin()) ? "DA" : "NE";
            }
            default ->
                throw new AssertionError();
        }
    }

}
