package com.example.tarottales.Model;

import java.util.List;

public class Element {
    private int id;
    private String name;
    private int image;
    private String description;
    private List<TarotCard> tarotCards;

    public Element(int id, String name, int image, String description, List<TarotCard> tarotCards) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.tarotCards = tarotCards;
    }

    public Element(String name, int image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Element() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TarotCard> getTarotCards() {
        return tarotCards;
    }

    public void setTarotCards(List<TarotCard> tarotCards) {
        this.tarotCards = tarotCards;
    }
}

