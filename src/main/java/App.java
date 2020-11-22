
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oSquadDao;
import dao.Sql2oHeroDao;
import models.Squad;
import models.Hero;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;


public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/herolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oHeroDao heroDao = new Sql2oHeroDao(sql2o);
        Sql2oSquadDao squadDao = new Sql2oSquadDao(sql2o);


        //get: show all tasks in all categories and show all categories
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            List<Hero> heroes = heroDao.getAll();
            model.put("heroes", heroes);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category
        get("/categories/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> squads = squadDao.getAll(); //refresh list of links for navbar
            model.put("squads", squads);
            return new ModelAndView(model, "squad-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new category
        post("/squads", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Squad newSquad = new Squad(name);
            squadDao.add(newSquad);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete all categories and all tasks
        get("/squads/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            squadDao.clearAllSquads();
            heroDao.clearAllHeroes();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all tasks
        get("/heroes/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            heroDao.clearAllHeroes();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific category (and the tasks it contains)
        get("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToFind = Integer.parseInt(req.params("id")); //new
            Squad foundSquad = squadDao.findById(idOfSquadToFind);
            model.put("squad", foundSquad);
            List<Hero> allHeroesBySquad = squadDao.getAllHeroesBySquad(idOfSquadToFind);
            model.put("heroes", allHeroesBySquad);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "squad-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a category
        get("/squads/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editSquad", true);
            Squad squad = squadDao.findById(Integer.parseInt(req.params("id")));
            model.put("squad", squad);
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "squad-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a category
        post("/squads/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newSquadName");
            squadDao.update(idOfSquadToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual task
        get("/squads/:squad_id/heroes/:hero_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToDelete = Integer.parseInt(req.params("hero_id"));
            heroDao.deleteById(idOfHeroToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new task form
        get("/heroes/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> squads = squadDao.getAll();
            model.put("squads", squads);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process new task form
        post("/heroes", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            String name = req.queryParams("name");
            int squadId = Integer.parseInt(req.queryParams("squadId"));
            Hero newHero = new Hero(name, squadId);        //See what we did with the hard coded categoryId?
            heroDao.add(newHero);
//            List<Task> tasksSoFar = taskDao.getAll();
//            for (Task taskItem: tasksSoFar
//                 ) {
//                System.out.println(taskItem);
//            }
//            System.out.println(tasksSoFar);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/squads/:squad_id/heroes/:hero_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToFind = Integer.parseInt(req.params("hero_id")); //pull id - must match route segment
            Hero foundHero = heroDao.findById(idOfHeroToFind); //use it to find task
            int idOfSquadToFind = Integer.parseInt(req.params("squad_id"));
            Squad foundSquad = squadDao.findById(idOfSquadToFind);
            model.put("squad", foundSquad);
            model.put("hero", foundHero); //add it to model for template to display
            model.put("squads", squadDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "hero-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a task
        get("/heroes/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Squad> allSquads = squadDao.getAll();
            model.put("squads", allSquads);
            Hero hero = heroDao.findById(Integer.parseInt(req.params("id")));
            model.put("hero", hero);
            model.put("editHero", true);
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a task
        post("/heroes/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int heroToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("name");
            int newSquadId = Integer.parseInt(req.queryParams("squadId"));
            heroDao.update(heroToEditId, newContent, newSquadId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
    }
}
