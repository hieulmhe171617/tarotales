package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.TarotCard;

import java.util.ArrayList;
import java.util.List;

public class TarotCardDAO extends DBContext{

    public TarotCardDAO(@Nullable Context context) {
        super(context);
    }

    public void insertCards(List<TarotCard> cards){
        SQLiteDatabase db = getWritableDatabase();
    }


    public TarotCard getRandomTarotCardForDailyDay() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TAROTCARD + " ORDER BY RANDOM() LIMIT 1", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
                @SuppressLint("Range") int cardNumber = cursor.getInt(cursor.getColumnIndex("cardNumber"));
                @SuppressLint("Range") String otherName = cursor.getString(cursor.getColumnIndex("otherName"));
                @SuppressLint("Range") String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
                @SuppressLint("Range") String overview = cursor.getString(cursor.getColumnIndex("overview"));
                @SuppressLint("Range") String job = cursor.getString(cursor.getColumnIndex("job"));
                @SuppressLint("Range") String love = cursor.getString(cursor.getColumnIndex("love"));
                @SuppressLint("Range") String finance = cursor.getString(cursor.getColumnIndex("finance"));
                @SuppressLint("Range") String health = cursor.getString(cursor.getColumnIndex("health"));
                @SuppressLint("Range") String spirit = cursor.getString(cursor.getColumnIndex("spirit"));
                TarotCard tarotCard = new TarotCard();
                tarotCard.setId(id);
                tarotCard.setName(name);
                tarotCard.setImage(image);
                tarotCard.setCardNumber(cardNumber);
                tarotCard.setOtherName(otherName);
                tarotCard.setKeyword(keyword);
                tarotCard.setOverview(overview);
                tarotCard.setJob(job);
                tarotCard.setLove(love);
                tarotCard.setFinance(finance);
                tarotCard.setHealth(health);
                tarotCard.setSpirit(spirit);
                cursor.close();
                return tarotCard;
            }
            cursor.close();
        }
        return null;
    }

    public TarotCard getTarotCardById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TAROTCARD + " where id = " + id, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
                @SuppressLint("Range") int cardNumber = cursor.getInt(cursor.getColumnIndex("cardNumber"));
                @SuppressLint("Range") String otherName = cursor.getString(cursor.getColumnIndex("otherName"));
                @SuppressLint("Range") String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
                @SuppressLint("Range") String overview = cursor.getString(cursor.getColumnIndex("overview"));
                @SuppressLint("Range") String job = cursor.getString(cursor.getColumnIndex("job"));
                @SuppressLint("Range") String love = cursor.getString(cursor.getColumnIndex("love"));
                @SuppressLint("Range") String finance = cursor.getString(cursor.getColumnIndex("finance"));
                @SuppressLint("Range") String health = cursor.getString(cursor.getColumnIndex("health"));
                @SuppressLint("Range") String spirit = cursor.getString(cursor.getColumnIndex("spirit"));
                TarotCard tarotCard = new TarotCard();
                tarotCard.setId(id);
                tarotCard.setName(name);
                tarotCard.setImage(image);
                tarotCard.setCardNumber(cardNumber);
                tarotCard.setOtherName(otherName);
                tarotCard.setKeyword(keyword);
                tarotCard.setOverview(overview);
                tarotCard.setJob(job);
                tarotCard.setLove(love);
                tarotCard.setFinance(finance);
                tarotCard.setHealth(health);
                tarotCard.setSpirit(spirit);
                cursor.close();
                return tarotCard;
            }
            cursor.close();
        }
        return null;
    }


    public List<Integer> getRandomTarotCardIdList(int number) {
        List<Integer> integers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TAROTCARD + " ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(number)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                integers.add(id);
            }
            cursor.close();
        }
        return integers;
    }


    public List<TarotCard> getTarotCardListRandom(int number) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_TAROTCARD + " ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(number)});
        List<TarotCard> cards = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
                @SuppressLint("Range") int cardNumber = cursor.getInt(cursor.getColumnIndex("cardNumber"));
                @SuppressLint("Range") String otherName = cursor.getString(cursor.getColumnIndex("otherName"));
                @SuppressLint("Range") String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
                @SuppressLint("Range") String overview = cursor.getString(cursor.getColumnIndex("overview"));
                @SuppressLint("Range") String job = cursor.getString(cursor.getColumnIndex("job"));
                @SuppressLint("Range") String love = cursor.getString(cursor.getColumnIndex("love"));
                @SuppressLint("Range") String finance = cursor.getString(cursor.getColumnIndex("finance"));
                @SuppressLint("Range") String health = cursor.getString(cursor.getColumnIndex("health"));
                @SuppressLint("Range") String spirit = cursor.getString(cursor.getColumnIndex("spirit"));
                TarotCard tarotCard = new TarotCard();
                tarotCard.setId(id);
                tarotCard.setName(name);
                tarotCard.setImage(image);
                tarotCard.setCardNumber(cardNumber);
                tarotCard.setOtherName(otherName);
                tarotCard.setKeyword(keyword);
                tarotCard.setOverview(overview);
                tarotCard.setJob(job);
                tarotCard.setLove(love);
                tarotCard.setFinance(finance);
                tarotCard.setHealth(health);
                tarotCard.setSpirit(spirit);
                cards.add(tarotCard);
            }
            cursor.close();
        }
        return cards;
    }

}
