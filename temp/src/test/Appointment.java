package test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


class Appointment {
    private Patient patient;
    private Physician physician;
    private LocalDateTime date;


    Appointment(Patient _patient, Physician _physician, LocalDateTime _date){
        this.patient = _patient;
        this.physician = _physician;
        this.date = _date;
    }

    Appointment(Patient _patient, Physician _physician, int year, int month, int day, int hour, int minute){
        this.patient = _patient;
        this.physician = _physician;
        this.date = LocalDateTime.of(year,month,day,hour,minute);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public LocalDateTime getDate() {
        return date;
    }


    public void setTimeAndDate(LocalDateTime _date) {
        this.date = _date;

    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

}
