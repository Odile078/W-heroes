package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Hero {

    private String name;
    private String power;
    private String weakness;
    private int age;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;

    public Hero(String name){
        this.name = name;
        this.power = power;
        this.weakness = weakness;
        this.age = age;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPower(String power) {
        this.power = power;
    }
    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPower(String power) {
        return power = power;
    }
    public String getWeakness(String weakness) {
        return weakness = weakness;
    }
    public int getAge(Integer age) {
        return age = age;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero)) return false;
        Hero hero = (Hero) o;
        return age == hero.age &&
                completed == hero.completed &&
                id == hero.id &&
                Objects.equals(name, hero.name) &&
                Objects.equals(power, hero.power) &&
                Objects.equals(weakness, hero.weakness) &&
                Objects.equals(createdAt, hero.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, power, weakness, age, completed, createdAt, id);
    }


}
