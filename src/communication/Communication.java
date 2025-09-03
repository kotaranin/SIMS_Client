/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import domain.*;
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
            System.exit(0);
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

    public StudentOfficer passwordLogIn(StudentOfficer studentOfficer) throws ClassNotFoundException, Exception {
        return (StudentOfficer) sendRequest(new Request(Operation.PASSWORD_LOG_IN, studentOfficer));
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

    public void insertCountry(Country country) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_COUNTRY, country));
    }

    public void updateCountry(Country country) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_COUNTRY, country));
    }

    public List<Country> searchCountries(String condition) throws ClassNotFoundException, Exception {
        return (List<Country>) sendRequest(new Request(Operation.SEARCH_COUNTRIES, condition));
    }

    public List<ExamPeriod> getAllExamPeriods() throws ClassNotFoundException, Exception {
        return (List<ExamPeriod>) sendRequest(new Request(Operation.GET_ALL_EXAM_PERIODS, null));
    }

    public void deleteExamPeriod(ExamPeriod examPeriod) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_EXAM_PERIOD, examPeriod));
    }

    public void insertExamPeriod(ExamPeriod examPeriod) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_EXAM_PERIOD, examPeriod));
    }

    public void updateExamPeriod(ExamPeriod examPeriod) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_EXAM_PERIOD, examPeriod));
    }

    public List<ExamPeriod> searchExamPeriods(String condition) throws ClassNotFoundException, Exception {
        return (List<ExamPeriod>) sendRequest(new Request(Operation.SEARCH_EXAM_PERIODS, condition));
    }

    public List<Teacher> getAllTeachers() throws ClassNotFoundException, Exception {
        return (List<Teacher>) sendRequest(new Request(Operation.GET_ALL_TEACHERS, null));
    }

    public void deleteTeacher(Teacher teacher) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_TEACHER, teacher));
    }

    public void insertTeacher(Teacher teacher) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_TEACHER, teacher));
    }

    public void updateTeacher(Teacher teacher) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_TEACHER, teacher));
    }

    public List<Teacher> searchTeachers(String condition) throws ClassNotFoundException, Exception {
        return (List<Teacher>) sendRequest(new Request(Operation.SEARCH_TEACHERS, condition));
    }

    public List<StudyLevel> getAllStudyLevels() throws ClassNotFoundException, Exception {
        return (List<StudyLevel>) sendRequest(new Request(Operation.GET_ALL_STUDY_LEVELS, null));
    }

    public void insertStudyLevel(StudyLevel studyLevel) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_STUDY_LEVEL, studyLevel));
    }

    public void updateStudyLevel(StudyLevel studyLevel) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_STUDY_LEVEL, studyLevel));
    }

    public List<StudyLevel> searchStudyLevels(String condition) throws ClassNotFoundException, Exception {
        return (List<StudyLevel>) sendRequest(new Request(Operation.SEARCH_STUDY_LEVEL, condition));
    }

    public List<Internship> getAllInternships() throws ClassNotFoundException, Exception {
        return (List<Internship>) sendRequest(new Request(Operation.GET_ALL_INTERNSHIPS, null));
    }

    public List<StudentOfficer> getAllStudentOfficers() throws ClassNotFoundException, Exception {
        return (List<StudentOfficer>) sendRequest(new Request(Operation.GET_ALL_STUDENT_OFFICERS, null));
    }

    public List<Student> getAllStudents() throws ClassNotFoundException, Exception {
        return (List<Student>) sendRequest(new Request(Operation.GET_ALL_STUDENTS, null));
    }

    public List<City> getCities(Country country) throws ClassNotFoundException, Exception {
        return (List<City>) sendRequest(new Request(Operation.GET_CITIES, country));
    }

    public List<StudyProgram> getStudyPrograms(StudyLevel studyLevel) throws ClassNotFoundException, Exception {
        return (List<StudyProgram>) sendRequest(new Request(Operation.GET_STUDY_PROGRAMS, studyLevel));
    }

    public List<domain.Module> getModules(StudyProgram studyProgram) throws ClassNotFoundException, Exception {
        return (List<domain.Module>) sendRequest(new Request(Operation.GET_MODULES, studyProgram));
    }

    public List<City> getAllCities() throws ClassNotFoundException, Exception {
        return (List<City>) sendRequest(new Request(Operation.GET_ALL_CITIES, null));
    }

    public List<StudyProgram> getAllStudyPrograms() throws ClassNotFoundException, Exception {
        return (List<StudyProgram>) sendRequest(new Request(Operation.GET_ALL_STUDY_PROGRAMS, null));
    }

    public List<domain.Module> getAllModules() throws ClassNotFoundException, Exception {
        return (List<domain.Module>) sendRequest(new Request(Operation.GET_ALL_MODULES, null));
    }

    public List<Student> searchStudents(Student student) throws ClassNotFoundException, Exception {
        return (List<Student>) sendRequest(new Request(Operation.SEARCH_STUDENTS, student));
    }

    public void updateStudent(Student student) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_STUDENT, student));
    }

    public void insertStudent(Student student) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_STUDENT, student));
    }

    public List<Company> getAllCompanies() throws ClassNotFoundException, Exception {
        return (List<Company>) sendRequest(new Request(Operation.GET_ALL_COMPANIES, null));
    }

    public void deleteInternship(Internship internship) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_INTERNSHIP, internship));
    }

    public void insertInternship(Internship internship) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_INTERNSHIP, internship));
    }

    public void updateInternship(Internship internship) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_INTERNSHIP, internship));
    }

    public void insertRegistrationRequest(RegistrationRequest registrationRequest) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_REGISTRATION_REQUEST, registrationRequest));
    }

    public void deleteRegistrationRequest(RegistrationRequest registrationRequest) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_REGISTRATION_REQUEST, registrationRequest));
    }

    public void insertStudentOfficer(StudentOfficer studentOfficer) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_STUDENT_OFFICER, studentOfficer));
    }

    public List<RegistrationRequest> getAllRegistrationRequests() throws ClassNotFoundException, Exception {
        return (List<RegistrationRequest>) sendRequest(new Request(Operation.GET_ALL_REGISTRATION_REQUESTS, null));
    }

    public void insertCompany(Company company) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.INSERT_COMPANY, company));
    }

    public void updateCompany(Company company) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_COMPANY, company));
    }

    public void updateStudentOfficer(StudentOfficer studentOfficer) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.UPDATE_STUDENT_OFFICER, studentOfficer));
    }

    public StudentOfficer questionLogIn(StudentOfficer studentOfficer) throws ClassNotFoundException, Exception {
        return (StudentOfficer) sendRequest(new Request(Operation.QUESTION_LOG_IN, studentOfficer));
    }

}
