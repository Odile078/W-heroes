package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeroTest {

    @Test
    public void hero_instantiatesCorrectly_true() {
        Hero newHero = Hero.setUpNewHero();
        assertTrue(newHero instanceof Hero);
    }
    @Test
    public void hero_getName_String() {
        Hero newHero = Hero.setUpNewHero();
        assertEquals("Captain America",newHero.getName());
    }
    @Test
    public void hero_getAge_Int() {
        Hero newHero = Hero.setUpNewHero();
        assertEquals(30,newHero.getAge());
    }
    @Test
    public void hero_getPower_String() {
        Hero newHero = Hero.setUpNewHero();
        assertEquals("strength",newHero.getPower());
    }
    @Test
    public void hero_getWeakness_String() {
        Hero newHero = Hero.setUpNewHero();
        assertEquals("music",newHero.getWeakness());
    }
    @Test
    public void newHero_getAllInstances_true() {
        Hero newHero = Hero.setUpNewHero();
        Hero another = Hero.setUpNewHero();
        assertTrue(Hero.getAllInstances().contains(newHero));
        assertTrue(Hero.getAllInstances().contains(another));
    }
    @Test
    public void newHero_getId_Int() {
        Hero.clearAllHeroes();
        Hero newHero = Hero.setUpNewHero();
        Hero another = Hero.setUpNewHero();
        Hero another1 = Hero.setUpNewHero();
        assertEquals(3,another1.getId());
    }
    @Test
    public void newHero_findById_id() {
        Hero.clearAllHeroes();
        Hero newHero = Hero.setUpNewHero();
        Hero another = Hero.setUpNewHero();
        assertEquals(2,Hero.findById(another.getId()).getId());
    }

}