package com.ziehro.luckyday;

public class Zodiac {

    private String fullName;
    private String email;
    private String position;
    private String description;

    public Zodiac(String fullName, String email, String position, String description) {
        this.fullName = fullName;
        this.email = email;
        this.position = position;
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}