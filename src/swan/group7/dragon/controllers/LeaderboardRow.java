package swan.group7.dragon.controllers;

public class LeaderboardRow {
    String Name;
    String Score;

    public LeaderboardRow(String Name, String Score) {
        this.Name = Name;
        this.Score = Score;
    }

    public String getName () {
        return Name;
    }

    public String getScore () {
        return Score;
    }
}
