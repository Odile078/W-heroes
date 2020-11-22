package models;

import java.util.Objects;
public class Squad {
    private String name;
    private String cause;
    private int size;
    private int id;

    public Squad(String name) {
        this.name = name;
        this.cause = cause;
        this.size = size;
    }

    public String getName() {
        return name;
    }
    public String getCause() {
        return cause;
    }
    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }
    public void setSize(int size) {
        this.size = size;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Squad)) return false;
        Squad squad = (Squad) o;
        return size == squad.size &&
                id == squad.id &&
                Objects.equals(name, squad.name) &&
                Objects.equals(cause, squad.cause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cause, size, id);
    }
}
