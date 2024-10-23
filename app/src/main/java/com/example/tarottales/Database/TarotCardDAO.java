package com.example.tarottales.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tarottales.Model.TarotCard;

import java.util.List;

public class TarotCardDAO extends DBContext{

    public TarotCardDAO(@Nullable Context context) {
        super(context);
    }

    public void insertCards(List<TarotCard> cards){
        SQLiteDatabase db = getWritableDatabase();
    }


}
