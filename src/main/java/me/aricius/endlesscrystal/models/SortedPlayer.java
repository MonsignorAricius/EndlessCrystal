package me.aricius.endlesscrystal.models;

public class SortedPlayer implements Comparable<SortedPlayer> {
    final String name;
    final int points;

    public SortedPlayer(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(SortedPlayer o) {
        if(this.getPoints() > o.getPoints()) {
            return -1;
        } else if(this.getPoints() < o.getPoints()) {
            return 1;
        }
        return this.getName().compareTo(o.getName());
    }
}
