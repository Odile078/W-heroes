package dao;


import models.Hero;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oHeroDaoTest {
    private Sql2oHeroDao heroDao; //ignore me for now. We'll create this soon.
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        heroDao = new Sql2oHeroDao(sql2o); //ignore me for now
        conn = sql2o.open(); //keep connection open through entire test so it does not get erased
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingHeroSetsId() throws Exception {
        Hero hero = setupNewHero();
        int originalHeroId = hero.getId();
        heroDao.add(hero);
        assertNotEquals(originalHeroId, hero.getId()); //how does this work?
    }
/*
    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Hero hero = setupNewHero();
        heroDao.add(hero); //add to dao (takes care of saving)
        Hero foundHero =  heroDao.findById(hero.getId()); //retrieve
        assertEquals(hero, foundHero); //should be the same
    }
*/
    @Test
    public void addedTasksAreReturnedFromgetAll() throws Exception {
        Hero hero = setupNewHero();
        heroDao.add(hero);
        assertEquals(1,  heroDao.getAll().size());
    }

    @Test
    public void noTasksReturnsEmptyList() throws Exception {
        assertEquals(0,  heroDao.getAll().size());
    }

    @Test
    public void updateChangesTaskContent() throws Exception {
        String initialName = "Lucky";
        Hero hero = setupNewHero();
        heroDao.add(hero);

        heroDao.update(hero.getId(),"Maze", 1);
        Hero updatedHero=  heroDao.findById(hero.getId()); //why do I need to refind this?
        assertNotEquals(initialName, updatedHero.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectTask() throws Exception {
        Hero hero = setupNewHero();
        heroDao.add(hero);
        heroDao.deleteById(hero.getId());
        assertEquals(0,  heroDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Hero hero = setupNewHero();
        Hero otherHero = new Hero("Maze", 2);
        heroDao.add(hero);
        heroDao.add(otherHero);
        int daoSize =  heroDao.getAll().size();
        heroDao.clearAllHeroes();
        assertTrue(daoSize > 0 && daoSize >  heroDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }

    @Test
    public void categoryIdIsReturnedCorrectly() throws Exception {
        Hero hero = setupNewHero();
        int originalSquadId = hero.getSquadId();
        heroDao.add(hero);
        assertEquals(originalSquadId,  heroDao.findById(hero.getId()).getSquadId());
    }

    //define the following once and then call it as above in your tests.
    public Hero setupNewHero(){
        return new Hero("Lucky", 1);
    }


}