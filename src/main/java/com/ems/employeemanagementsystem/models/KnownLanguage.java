package com.ems.employeemanagementsystem.models;

public class KnownLanguage {
    String languageName;
    int scoreOutOf100;

    public KnownLanguage(String languageName, int scoreOutOf100) {
        this.languageName = languageName;
        this.scoreOutOf100 = scoreOutOf100;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getScoreOutOf100() {
        return scoreOutOf100;
    }

    public void setScoreOutOf100(int scoreOutOf100) {
        this.scoreOutOf100 = scoreOutOf100;
    }
}
