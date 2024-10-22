package com.example.tarottales.Model;

import java.io.Serializable;
import java.util.List;

public class TarotCard implements Serializable {
    private int id;
    private String name;
    private int image;
    private int cardNumber;
    private String otherName;
    private String keyword;
    private String overview;
    private String job;
    private String love;
    private String finance;
    private String health;
    private String spirit;

    //ref
    private List<Element> elements;
    private List<Planet> planets;
    private List<Zodiac> zodiacs;


    public TarotCard(String name, int image, int cardNumber, String otherName, String keyword, String overview, String job, String love, String finance, String health, String spirit, List<Element> elements, List<Planet> planets, List<Zodiac> zodiacs) {
        this.name = name;
        this.image = image;
        this.cardNumber = cardNumber;
        this.otherName = otherName;
        this.keyword = keyword;
        this.overview = overview;
        this.job = job;
        this.love = love;
        this.finance = finance;
        this.health = health;
        this.spirit = spirit;
        this.elements = elements;
        this.planets = planets;
        this.zodiacs = zodiacs;
    }

    public TarotCard(String name, int image, int cardNumber, String otherName, String keyword, String overview, String job, String love, String finance, String health, String spirit) {
        this.name = name;
        this.image = image;
        this.cardNumber = cardNumber;
        this.otherName = otherName;
        this.keyword = keyword;
        this.overview = overview;
        this.job = job;
        this.love = love;
        this.finance = finance;
        this.health = health;
        this.spirit = spirit;
    }

    public TarotCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getSpirit() {
        return spirit;
    }

    public void setSpirit(String spirit) {
        this.spirit = spirit;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    public List<Zodiac> getZodiacs() {
        return zodiacs;
    }

    public void setZodiacs(List<Zodiac> zodiacs) {
        this.zodiacs = zodiacs;
    }
}
