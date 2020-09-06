package project.reimbursement.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import project.reimbursement.enums.CityType;

public class DayTest {

    @Test
    public void testHashCode() {
        Date date = new Date();

        Day day1 = new Day(date, CityType.LOW_COST);

        assertEquals(day1.hashCode(), date.hashCode());
    }

    @Test
    public void testDay() {
        Date date = new Date();
        Day day = new Day(date, CityType.LOW_COST);
        assertEquals(day.getType(), CityType.LOW_COST);
        assertEquals(day.getDate(), date);
    }

    @Test
    public void testGetPreviousDayBeforeSet() {
        Date date = new Date();
        Day day = new Day(date, CityType.LOW_COST);
        assertTrue(day.getPreviousDay() != null);
        assertTrue(!day.getPreviousDay().isPresent());
    }

    @Test
    public void testGetPreviousDayAfterSet() {
        Date date = new Date();
        Day lastDay = new Day(date, CityType.HIGH_COST);
        Day day = new Day(date, CityType.LOW_COST);
        day.setPreviousDay(lastDay);
        assertTrue(day.getPreviousDay() != null);
        assertTrue(day.getPreviousDay().isPresent());
        assertTrue(day.getPreviousDay().get().getDate().equals(date));
    }

    @Test
    public void testGetNextDayBeforeSet() {
        Date date = new Date();
        Day day = new Day(date, CityType.LOW_COST);
        assertTrue(day.getPreviousDay() != null);
        assertTrue(!day.getPreviousDay().isPresent());
    }

    @Test
    public void testGetNextDayAfterSet() {
        Date date = new Date();
        Day lastDay = new Day(date, CityType.HIGH_COST);
        Day day = new Day(date, CityType.LOW_COST);
        day.setNextDay(lastDay);
        assertTrue(day.getNextDay() != null);
        assertTrue(day.getNextDay().isPresent());
        assertTrue(day.getNextDay().get().getDate().equals(date));
    }

    @Test
    public void testEqualsObject() {
        Date date = new Date();
        Day day1 = new Day(date, CityType.LOW_COST);
        Day day2 = new Day(date, CityType.HIGH_COST);
        assertEquals(day1, day2);
    }

}
