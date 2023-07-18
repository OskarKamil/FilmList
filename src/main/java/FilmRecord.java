package main.java;

import java.util.Scanner;

public class FilmRecord {
    private String englishTitle;
    private String originalTitle;
    private String type;
    private short releaseYear;
    private short rating;
    private short watchDate;
    private String comments;

    public FilmRecord(String englishTitle, String originalTitle, String type, short releaseYear, short rating, short watchDate, String comments) {
        //LocalDate today = LocalDate.now();
        this.englishTitle = englishTitle;
        this.originalTitle = originalTitle;
        this.type = type;
        //    if (releaseYear < 1900 || releaseYear > today.getYear())
        //     throw new IllegalArgumentException("Release year must be between 1900 and today");
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.watchDate = watchDate;
        this.comments = comments;
    }

    public FilmRecord() {
    }

    public void addFilmRecordFromKeyboard(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Adding film record. Please enter the following details:");
        System.out.println("English title:");
        this.setEnglishTitle(keyboard.nextLine());
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(short releaseYear) {
//        LocalDate today = LocalDate.now();
//        if (releaseYear < 1900 || releaseYear > today.getYear())
//            throw new IllegalArgumentException("Release year must be between 1900 and today");
        this.releaseYear = releaseYear;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    public short getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(short watchDate) {
        this.watchDate = watchDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
