package Data;

import lombok.Getter;
import lombok.Setter;

public class Player {

    @Setter
    @Getter
    private int age;

    @Setter
    @Getter
    private Team team;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String surname;

    public Player(int age, Team team, String name, String surname) {
        this.age = age;
        this.team = team;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + age;
        hash = 19 * hash + team.hashCode();
        hash = 19 * hash + name.hashCode();
        hash = 19 * hash + surname.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + String.valueOf(age);
    }
}
