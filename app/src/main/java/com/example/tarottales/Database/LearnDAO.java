package com.example.tarottales.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.Element;
import com.example.tarottales.Model.Planet;
import com.example.tarottales.Model.TarotCard;
import com.example.tarottales.Model.Zodiac;

import java.util.ArrayList;
import java.util.List;

public class LearnDAO extends DBContext {
    public LearnDAO(@Nullable Context context) {
        super(context);
    }

    //<editor-fold desc="Get card list, detail with references">

    // Get list card - return List
    @SuppressLint("Range")
    public List<TarotCard> getListCard() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_TAROTCARD;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        // Convert Cursor to list
        List<TarotCard> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TarotCard newCard = new TarotCard();
                newCard.setId(cursor.getInt(cursor.getColumnIndex("id")));
                newCard.setName(cursor.getString(cursor.getColumnIndex("name")));
                newCard.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                newCard.setCardNumber(cursor.getInt(cursor.getColumnIndex("cardNumber")));
                newCard.setOtherName(cursor.getString(cursor.getColumnIndex("otherName")));
                newCard.setKeyword(cursor.getString(cursor.getColumnIndex("keyword")));
                newCard.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                newCard.setJob(cursor.getString(cursor.getColumnIndex("job")));
                newCard.setLove(cursor.getString(cursor.getColumnIndex("love")));
                newCard.setFinance(cursor.getString(cursor.getColumnIndex("finance")));
                newCard.setHealth(cursor.getString(cursor.getColumnIndex("health")));
                newCard.setSpirit(cursor.getString(cursor.getColumnIndex("spirit")));
                list.add(newCard);
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Get Card detail with references
    @SuppressLint("Range")
    public TarotCard getCardDetailByCardId(int cardId) {
        SQLiteDatabase db = getReadableDatabase();
        TarotCard cardDetail = new TarotCard();
        // set references
        cardDetail.setElements(getListElementByCardId(cardId));
        cardDetail.setPlanets(getListPlanetByCardId(cardId));
        cardDetail.setZodiacs(getListZodiacByCardId(cardId));
        // set detail
        String sql = "select * from " + TB_TAROTCARD + " where id = ?;";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cardId)});
        if (cursor.moveToFirst()) {
            cardDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cardDetail.setName(cursor.getString(cursor.getColumnIndex("name")));
            cardDetail.setImage(cursor.getInt(cursor.getColumnIndex("image")));
            cardDetail.setCardNumber(cursor.getInt(cursor.getColumnIndex("cardNumber")));
            cardDetail.setOtherName(cursor.getString(cursor.getColumnIndex("otherName")));
            cardDetail.setKeyword(cursor.getString(cursor.getColumnIndex("keyword")));
            cardDetail.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
            cardDetail.setJob(cursor.getString(cursor.getColumnIndex("job")));
            cardDetail.setLove(cursor.getString(cursor.getColumnIndex("love")));
            cardDetail.setFinance(cursor.getString(cursor.getColumnIndex("finance")));
            cardDetail.setHealth(cursor.getString(cursor.getColumnIndex("health")));
            cardDetail.setSpirit(cursor.getString(cursor.getColumnIndex("spirit")));
            return cardDetail;
        }
        return null;
    }

    // Get list element by card id
    @SuppressLint("Range")
    public List<Element> getListElementByCardId(int cardId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Element> result = new ArrayList<>();
        String sql = "select e.* from " + TB_ELEMENT + " e\n" +
                "join " + TB_TAROTCARD_ELEMENT + " tc_e on e.id = tc_e.elementId\n" +
                "join " + TB_TAROTCARD + " tc on tc_e.tarotCardId = tc.id\n" +
                "where tc.id = ?;";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cardId)});
        if (cursor.moveToFirst()) {
            do {
                Element element = new Element();
                element.setId(cursor.getInt(cursor.getColumnIndex("id")));
                element.setName(cursor.getString(cursor.getColumnIndex("name")));
                element.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                element.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                result.add(element);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Get list planet by card id
    @SuppressLint("Range")
    public List<Planet> getListPlanetByCardId(int cardId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Planet> result = new ArrayList<>();
        String sql = "select p.* from " + TB_PLANET + " p\n" +
                "join " + TB_TAROTCARD_PLANET + " tc_p on p.id = tc_p.planetId\n" +
                "join " + TB_TAROTCARD + " tc on tc_p.tarotCardId = tc.id\n" +
                "where tc.id = ?;";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cardId)});
        if (cursor.moveToFirst()) {
            do {
                Planet planet = new Planet();
                planet.setId(cursor.getInt(cursor.getColumnIndex("id")));
                planet.setName(cursor.getString(cursor.getColumnIndex("name")));
                planet.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                planet.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                result.add(planet);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Get list zodiac by card id
    @SuppressLint("Range")
    public List<Zodiac> getListZodiacByCardId(int cardId) {
        SQLiteDatabase db = getReadableDatabase();
        List<Zodiac> result = new ArrayList<>();
        String sql = "select z.* from " + TB_ZODIAC + " z\n" +
                "join " + TB_TAROTCARD_ZODIAC + " tc_z on z.id = tc_z.zodiacId\n" +
                "join " + TB_TAROTCARD + " tc on tc_z.tarotCardId = tc.id\n" +
                "where tc.id = ?;";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cardId)});
        if (cursor.moveToFirst()) {
            do {
                Zodiac zodiac = new Zodiac();
                zodiac.setId(cursor.getInt(cursor.getColumnIndex("id")));
                zodiac.setName(cursor.getString(cursor.getColumnIndex("name")));
                zodiac.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                result.add(zodiac);
            } while (cursor.moveToNext());
        }
        return result;
    }

    //</editor-fold>

    //<editor-fold desc="Get Element list, detail with references">

    // Get list Element - return list
    @SuppressLint("Range")
    public List<Element> getListElement() {
        List<Element> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_ELEMENT;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Element e = new Element();
                e.setId(cursor.getInt(cursor.getColumnIndex("id")));
                e.setName(cursor.getString(cursor.getColumnIndex("name")));
                e.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                e.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                result.add(e);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // get element detail
    @SuppressLint("Range")
    public Element getElementDetailByElementId(int elementId) {
        Element elementDetail = new Element();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_ELEMENT + " where id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(elementId)});

        if (cursor.moveToFirst()) {
            elementDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            elementDetail.setName(cursor.getString(cursor.getColumnIndex("name")));
            elementDetail.setImage(cursor.getInt(cursor.getColumnIndex("image")));
            elementDetail.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            return elementDetail;
        }
        return null;
    }

    // get list card of element id
    @SuppressLint("Range")
    public List<TarotCard> getListCardByElementId(int elementId) {
        List<TarotCard> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select tc.* from " + TB_ELEMENT + " e\n" +
                "join " + TB_TAROTCARD_ELEMENT + " tc_e on e.id = tc_e.elementId\n" +
                "join " + TB_TAROTCARD + " tc on tc_e.tarotCardId = tc.id\n" +
                "where e.id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(elementId)});

        if (cursor.moveToFirst()) {
            do {
                TarotCard card = new TarotCard();
                card.setId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                card.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                card.setCardNumber(cursor.getInt(cursor.getColumnIndex("cardNumber")));
                card.setOtherName(cursor.getString(cursor.getColumnIndex("otherName")));
                card.setKeyword(cursor.getString(cursor.getColumnIndex("keyword")));
                card.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                card.setJob(cursor.getString(cursor.getColumnIndex("job")));
                card.setLove(cursor.getString(cursor.getColumnIndex("love")));
                card.setFinance(cursor.getString(cursor.getColumnIndex("finance")));
                card.setHealth(cursor.getString(cursor.getColumnIndex("health")));
                card.setSpirit(cursor.getString(cursor.getColumnIndex("spirit")));
                result.add(card);
            } while (cursor.moveToNext());
        }
        return result;
    }

    //</editor-fold>

    //<editor-fold desc="Get Planet list, detail with references">

    // get list Planet
    @SuppressLint("Range")
    public List<Planet> getListPlanet() {
        List<Planet> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_PLANET;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Planet p = new Planet();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setName(cursor.getString(cursor.getColumnIndex("name")));
                p.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                p.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                result.add(p);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // get planet detail
    @SuppressLint("Range")
    public Planet getPlanetDetailByPlanetId(int planetId) {
        Planet planetDetail = new Planet();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_PLANET + " where id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(planetId)});
        if(cursor.moveToFirst()){
            planetDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            planetDetail.setName(cursor.getString(cursor.getColumnIndex("name")));
            planetDetail.setImage(cursor.getInt(cursor.getColumnIndex("image")));
            planetDetail.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            return planetDetail;
        }
        return null;
    }

    // get list card of planet id
    @SuppressLint("Range")
    public List<TarotCard> getListCardByPlanetId(int planetId) {
        List<TarotCard> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select tc.* from "+TB_PLANET+" p\n" +
                "join "+TB_TAROTCARD_PLANET+" tc_p on p.id = tc_p.planetId\n" +
                "join "+TB_TAROTCARD+" tc on tc_p.tarotCardId = tc.id\n" +
                "where p.id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(planetId)});
        if (cursor.moveToFirst()) {
            do {
                TarotCard card = new TarotCard();
                card.setId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                card.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                card.setCardNumber(cursor.getInt(cursor.getColumnIndex("cardNumber")));
                card.setOtherName(cursor.getString(cursor.getColumnIndex("otherName")));
                card.setKeyword(cursor.getString(cursor.getColumnIndex("keyword")));
                card.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                card.setJob(cursor.getString(cursor.getColumnIndex("job")));
                card.setLove(cursor.getString(cursor.getColumnIndex("love")));
                card.setFinance(cursor.getString(cursor.getColumnIndex("finance")));
                card.setHealth(cursor.getString(cursor.getColumnIndex("health")));
                card.setSpirit(cursor.getString(cursor.getColumnIndex("spirit")));
                result.add(card);
            } while (cursor.moveToNext());
        }
        return result;
    }

    //</editor-fold>

    //<editor-fold desc="Get Zodiac list, detail with references">

    // get list zodiac
    @SuppressLint("Range")
    public List<Zodiac> getListZodiac() {
        List<Zodiac> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_ZODIAC;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Zodiac z = new Zodiac();
                z.setId(cursor.getInt(cursor.getColumnIndex("id")));
                z.setName(cursor.getString(cursor.getColumnIndex("name")));
                z.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                z.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                result.add(z);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // get zodiac detail
    @SuppressLint("Range")
    public Zodiac getZodiacDetailByZodiacId(int zodiacId) {
        Zodiac zodiacDetail = new Zodiac();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TB_ZODIAC + " where id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(zodiacId)});
        if(cursor.moveToFirst()){
            zodiacDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
            zodiacDetail.setName(cursor.getString(cursor.getColumnIndex("name")));
            zodiacDetail.setImage(cursor.getInt(cursor.getColumnIndex("image")));
            zodiacDetail.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            return zodiacDetail;
        }
        return null;
    }

    // get list card of zodiac id
    @SuppressLint("Range")
    public List<TarotCard> getListCardByZodiacId(int zodiacId) {
        List<TarotCard> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select tc.* from "+TB_ZODIAC+" z\n" +
                "join "+TB_TAROTCARD_ZODIAC+" tc_z on z.id = tc_z.zodiacId\n" +
                "join "+TB_TAROTCARD+" tc on tc_z.tarotCardId = tc.id\n" +
                "where z.id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(zodiacId)});
        if (cursor.moveToFirst()) {
            do {
                TarotCard card = new TarotCard();
                card.setId(cursor.getInt(cursor.getColumnIndex("id")));
                card.setName(cursor.getString(cursor.getColumnIndex("name")));
                card.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                card.setCardNumber(cursor.getInt(cursor.getColumnIndex("cardNumber")));
                card.setOtherName(cursor.getString(cursor.getColumnIndex("otherName")));
                card.setKeyword(cursor.getString(cursor.getColumnIndex("keyword")));
                card.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                card.setJob(cursor.getString(cursor.getColumnIndex("job")));
                card.setLove(cursor.getString(cursor.getColumnIndex("love")));
                card.setFinance(cursor.getString(cursor.getColumnIndex("finance")));
                card.setHealth(cursor.getString(cursor.getColumnIndex("health")));
                card.setSpirit(cursor.getString(cursor.getColumnIndex("spirit")));
                result.add(card);
            } while (cursor.moveToNext());
        }
        return result;
    }

    //</editor-fold>


}
