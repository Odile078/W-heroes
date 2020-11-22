package models;


import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class HeroTest {

    @Test
    public void NewTaskObjectGetsCorrectlyCreated_true() throws Exception {
        Hero hero = setupNewHero();
        assertEquals(true, hero instanceof Hero);
    }

    @Test
    public void TaskInstantiatesWithDescription_true() throws Exception {
        Hero hero = setupNewHero();
        assertEquals("Lucky", hero.getName());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Hero hero = setupNewHero();
        assertEquals(false, hero.getCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Hero hero = setupNewHero();
        assertEquals(LocalDateTime.now().getDayOfWeek(), hero.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Hero setupNewHero(){
        return new Hero("Lucky");
    }

}