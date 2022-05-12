package Models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    Patient patient = new Patient("12345NN","Cian","Farrell",
            LocalDate.of(2010,2,1),"1234","123@email.com",
            "n/a","n/a","n/a");
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        for(int i = 0; i<10;++i)
            patient.addRecord(LocalDate.of(2021,1,i+1),"Pfizer","1234"+i);

    }

    @AfterEach
    void tearDown() {
        patient.clearRecords();
    }
    @Test
    void getRecord() {
        assertEquals("12343",patient.getRecord(3).getBatchNum());
        assertEquals("12348",patient.getRecord(8).getBatchNum());
        patient.deleteRecord(8);
        assertEquals("12349",patient.getRecord(8).getBatchNum());
        patient.deleteRecord(3);
        assertEquals("12344",patient.getRecord(3).getBatchNum());
    }

    @Test
    void deleteRecord() {
        assertEquals("12349",patient.getRecord(9).getBatchNum());
        patient.deleteRecord(9);
        assertNull(patient.getRecord(9));
        assertEquals(9,patient.recordAmount());
    }

    @Test
    void editRecord() {
        assertEquals("12343",patient.getRecord(3).getBatchNum());
        assertEquals("12348",patient.getRecord(8).getBatchNum());
        patient.editRecord(3,new VaccinationRecord(LocalDate.of(2009,1,1),"Pfizer","111"));
        assertEquals("111",patient.getRecord(3).getBatchNum());
        patient.editRecord(8,new VaccinationRecord(LocalDate.of(2009,1,1),"Pfizer","222"));
        assertEquals("222",patient.getRecord(8).getBatchNum());
    }

    @Test
    void addRecord() {
        assertEquals(10,patient.recordAmount());
        assertNull(patient.getRecord(10));
        patient.addRecord(LocalDate.of(2009,1,1),"Pfizer","12351");
        assertEquals("12351",patient.getRecord(10).getBatchNum());
    }

}