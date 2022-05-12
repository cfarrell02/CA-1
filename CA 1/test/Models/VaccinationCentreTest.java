package Models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VaccinationCentreTest {
    VaccinationCentre vaccinationCentre = new VaccinationCentre("Test","1 Yellow Road","X91 12345",222);
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        for(int i = 0; i<10;++i)
            vaccinationCentre.addBooth("A"+i,"Ba"+(i+1),i+1);

    }

    @AfterEach
    void tearDown() {
        vaccinationCentre.clearBooths();
    }

    @Test
    void addBooth() {
        assertEquals(10,vaccinationCentre.boothAmount());
        assertNull(vaccinationCentre.getBooth(10));
        vaccinationCentre.addBooth("B1","Test",1);
        assertEquals("B1",vaccinationCentre.getBooth(10).getBoothID());
    }

    @Test
    void deleteBooth() {
        assertEquals("A9",vaccinationCentre.getBooth(9).getBoothID());
        vaccinationCentre.deleteBooth(9);
        assertNull(vaccinationCentre.getBooth(9));
        assertEquals(9,vaccinationCentre.boothAmount());
    }

    @Test
    void getBooth() {
        assertEquals("A3",vaccinationCentre.getBooth(3).getBoothID());
        assertEquals("A8",vaccinationCentre.getBooth(8).getBoothID());
        vaccinationCentre.deleteBooth(8);
        assertEquals("A9",vaccinationCentre.getBooth(8).getBoothID());
        vaccinationCentre.deleteBooth(3);
        assertEquals("A4",vaccinationCentre.getBooth(3).getBoothID());
    }

    @Test
    void editBooth() {
        assertEquals("A3",vaccinationCentre.getBooth(3).getBoothID());
        assertEquals("A8",vaccinationCentre.getBooth(8).getBoothID());
        vaccinationCentre.editBooth(3,new VaccinationBooth("C3","New",2));
        assertEquals("C3",vaccinationCentre.getBooth(3).getBoothID());
        vaccinationCentre.editBooth(8,new VaccinationBooth("C8","New",2));
        assertEquals("C8",vaccinationCentre.getBooth(8).getBoothID());
    }

}