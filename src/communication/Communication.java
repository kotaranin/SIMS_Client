/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import domain.Country;
import domain.Report;
import domain.StudentOfficer;
import enums.Operation;
import enums.ResultType;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.*;

/**
 *
 * @author kotar
 */
public class Communication {

    private static Communication instance;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    private Communication() {
        try {
            this.socket = new Socket("localhost", 9000);
            this.sender = new Sender(socket);
            this.receiver = new Receiver(socket);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    private Object sendRequest(Request request) throws IOException, ClassNotFoundException, Exception {
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getResultType() == ResultType.SUCCESS) {
            return response.getArgument();
        } else {
            throw response.getException();
        }
    }

    public StudentOfficer logIn(StudentOfficer studentOfficer) throws ClassNotFoundException, Exception {
        return (StudentOfficer) sendRequest(new Request(Operation.LOG_IN, studentOfficer));
    }

    public List<Report> getAllReports() throws ClassNotFoundException, Exception {
        return (List<Report>) sendRequest(new Request(Operation.GET_ALL_REPORTS, null));
    }

    public void deleteReport(Report report) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_REPORT, report));
    }

    public void insertReport(Report report) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_REPORT, report));
    }

    public void updateReport(Report report) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_REPORT, report));
    }

    public List<Report> searchReports(String condition) throws ClassNotFoundException, Exception {
        return (List<Report>) sendRequest(new Request(Operation.SEARCH_REPORTS, condition));
    }

    public List<Country> getAllCountries() throws ClassNotFoundException, Exception {
        return (List<Country>) sendRequest(new Request(Operation.GET_ALL_COUNTRIES, null));
    }

    public void deleteCountry(Country country) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_COUNTRY, country));
    }

    public void insertCountry(Country country) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_COUNTRY, country));
    }

    public void updateCountry(Country country) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_COUNTRY, country));
    }

    public List<Country> searchCountries(String condition) throws ClassNotFoundException, Exception {
        return (List<Country>) sendRequest(new Request(Operation.SEARCH_COUNTRIES, condition));
    }

}
