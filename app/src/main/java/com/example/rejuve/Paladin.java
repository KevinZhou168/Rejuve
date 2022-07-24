package com.example.rejuve;

public class Paladin implements Comparable<Paladin> {
    private int points, drinks, streak;
    private String guildCode, name;
    private boolean streakIncremented, exercised;


    public Paladin(String name, int points, int drinks, int streak, String guildCode) {
        this.name = name;
        this.points = points;
        this.drinks = drinks;
        this.streak = streak;
        this.guildCode = guildCode;
        this.streakIncremented = false;
        this.exercised = false;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDrinks() {
        return drinks;
    }

    public void setDrinks(int drinks) {
        this.drinks = drinks;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getGuildCode() {
        return guildCode;
    }

    public void setGuildCode(String guildCode) {
        this.guildCode = guildCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String str = "\n\n";
        str += "Name: " + name;
        str += "Guild Code: " + guildCode;
        str += "\nPoints: " + points;
        str += "\nDrinks: " + drinks;
        str += "\nStreak: " + streak;
        str += "\nStreak Status: " + streakIncremented;
        str += "\nExercise Status: " + exercised;
        return str;
    }

    @Override
    public int compareTo(Paladin paladin) {
        int comparePoints = paladin.getPoints();
        return comparePoints - this.points;
    }

    public boolean isStreakIncremented() {
        return streakIncremented;
    }

    public void setStreakIncremented(boolean streakIncremented) {
        this.streakIncremented = streakIncremented;
    }

    public boolean isExercised() {
        return exercised;
    }

    public void setExercised(boolean exercised) {
        this.exercised = exercised;
    }
}
