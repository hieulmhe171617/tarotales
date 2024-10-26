package com.example.tarottales.file;

import android.content.Context;

import com.example.tarottales.dto.TopicHistoryDTO;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonTopicHistoryDTOHelper {
    public static final String FILE_NAME = "topicHistory.json";

    public static void saveTopicHistoryDTOToJson(Context context, List<TopicHistoryDTO> list, String fileName) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TopicHistoryDTO> readTopicHistoryDToFromJson(Context context, String fileName) {
        List<TopicHistoryDTO> list = null;
        Gson gson = new Gson();
        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            // doc json
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            // xac dinh kieu danh sach
            Type productListType = new TypeToken<List<TopicHistoryDTO>>() {}.getType();
            // convert tu json sang list
            list = gson.fromJson(jsonString.toString(), productListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void addTopicHistoryDTO(Context context, TopicHistoryDTO topicHistoryDTO, String fileName) {
        //doc danh sach
        List<TopicHistoryDTO> currentList = readTopicHistoryDToFromJson(context, fileName);
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        //add
        currentList.add(0,topicHistoryDTO);
        //ghi lai
        saveTopicHistoryDTOToJson(context, currentList, fileName);
    }


    public static void updateNote(Context context, String date, String time, String newNote, String fileName) {
        List<TopicHistoryDTO> currentList = readTopicHistoryDToFromJson(context, fileName);
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        for (TopicHistoryDTO topic : currentList) {
            if (topic.getDate().equals(date) && topic.getTime().equals(time)) {
                topic.setNote(newNote);
                break;
            }
        }
        saveTopicHistoryDTOToJson(context, currentList, fileName);
    }

}
