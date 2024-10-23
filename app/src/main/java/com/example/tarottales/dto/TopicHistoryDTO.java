package com.example.tarottales.dto;

import com.example.tarottales.Model.TarotCard;

import java.io.Serializable;
import java.util.List;

public class TopicHistoryDTO implements Serializable {
    private String date;
    private String time;
    private String note;
    private TarotCard card1;
    private TarotCard card2;
    private TarotCard card3;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TarotCard getCard1() {
        return card1;
    }

    public void setCard1(TarotCard card1) {
        this.card1 = card1;
    }

    public TarotCard getCard2() {
        return card2;
    }

    public void setCard2(TarotCard card2) {
        this.card2 = card2;
    }

    public TarotCard getCard3() {
        return card3;
    }

    public void setCard3(TarotCard card3) {
        this.card3 = card3;
    }

    public TopicHistoryDTO() {
    }
}
