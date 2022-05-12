package Models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VaccinationBoothTest {
    VaccinationBooth booth = new VaccinationBooth("A1","N/A",1);
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        for(int i = 0; i<10;++i)
            booth.addAppointment(LocalDate.of(2000+i,i+1,1),"10:00","Pfizer","1122","N/A","1234"+i+"N");

    }

    @AfterEach
    void tearDown() {
        booth.clearAppointments();
    }
    @Test
    void addAppointment() {
        assertEquals(10,booth.appointmentAmount());
        assertNull(booth.getAppointment(10));
        booth.addAppointment(LocalDate.of(2009,1,1),"10:00","Pfizer","1234PP","N/A","1234NN");
        assertEquals("1234NN",booth.getAppointment(10).getPatientPPSN());
    }

    @Test
    void deleteAppointment() {
            assertEquals("12349N",booth.getAppointment(9).getPatientPPSN());
            booth.deleteAppointment(9);
            assertNull(booth.getAppointment(9));
            assertEquals(9,booth.appointmentAmount());
    }

    @Test
    void getAppointment() {
        assertEquals("12343N",booth.getAppointment(3).getPatientPPSN());
        assertEquals("12348N",booth.getAppointment(8).getPatientPPSN());
        booth.deleteAppointment(8);
        assertEquals("12349N",booth.getAppointment(8).getPatientPPSN());
        booth.deleteAppointment(3);
        assertEquals("12344N",booth.getAppointment(3).getPatientPPSN());
    }

    @Test
    void editAppointment() {
        assertEquals("12343N",booth.getAppointment(3).getPatientPPSN());
        assertEquals("12348N",booth.getAppointment(8).getPatientPPSN());
        booth.editAppointment(3,new VaccinationAppointment(LocalDate.of(2009,1,1),"10:00","Pfizer","1234PP","N/A","1234NN"));
        assertEquals("1234NN",booth.getAppointment(3).getPatientPPSN());
        booth.editAppointment(8,new VaccinationAppointment(LocalDate.of(2009,1,1),"10:00","Pfizer","1234PP","N/A","1234NN"));
        assertEquals("1234NN",booth.getAppointment(3).getPatientPPSN());
    }

}