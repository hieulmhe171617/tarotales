package com.example.tarottales.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tarottales.Model.Message;
import com.example.tarottales.adapter.MessageAdapter;
import com.example.tarottales.R;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class ChatFragment extends Fragment {
    private static final String CHAT_HISTORY_FILE = "chat_history.json";
    private String apiKey = "AIzaSyA4mTa5P9V8QtEVOPecbktLdd6LJ5umvHI";
    RecyclerView recyclerView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }

    private void bindingView(View view) {
        messageList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        messageEditText = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_btn);
    }

    private void bindingAction() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = messageEditText.getText().toString().trim();
                if (!question.isEmpty()) {
                    addToChat(question, Message.SENT_BY_ME);
                    messageEditText.setText("");
                    callGeminiAPI(question);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction();
        loadChatHistory();

        if (getArguments() != null) {
            String initialText = getArguments().getString("initialText");
            if (initialText != null) {
                messageEditText.setText(initialText);
            }
        }

        // Setup RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
    }

    private void addToChat(String message, String sentBy) {
        getActivity().runOnUiThread(() -> {
            synchronized (messageList) {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }


    private void addResponse(String response) {
        messageList.remove(messageList.size() - 1); // Remove "Typing..." message
        // Thực hiện markdown cho tin nhắn với kí tự ** hoặc ##
        addToChat(response, Message.SENT_BY_BOT);
        saveChatHistory();
    }

    private void callGeminiAPI(String question) {
        // Add "Typing..." message
        messageList.add(new Message("Typing...", Message.SENT_BY_BOT));

        // Setup Google Gemini model
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        // Prepare content for Gemini
        Content content = new Content.Builder()
                .addText(question)
                .build();

        // Create an executor for handling the result
        Executor executor = Executors.newSingleThreadExecutor();

        // Call Gemini API
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                if (result != null && result.getText() != null) {
                    String resultText = result.getText();
                    addResponse(resultText.trim()); // Thêm phản hồi thực từ AI và lưu lại
                    saveChatHistory();
                } else {
                    addResponse("No response received.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                addResponse("Failed to load response: " + t.getMessage());
            }
        }, executor);
    }
    private void saveChatHistory() {
        JSONArray jsonArray = new JSONArray();

        synchronized (messageList) {
            for (Message message : messageList) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("text", message.getMessage());
                    jsonObject.put("sentBy", message.getSentBy());
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try (FileOutputStream fos = getContext().openFileOutput(CHAT_HISTORY_FILE, Context.MODE_PRIVATE)) {
            fos.write(jsonArray.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm đọc lại lịch sử chat từ JSON khi khởi tạo fragment
    private void loadChatHistory() {
        try (FileInputStream fis = getContext().openFileInput(CHAT_HISTORY_FILE)) {
            byte[] data = new byte[fis.available()];
            fis.read(data);
            String jsonString = new String(data);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String text = jsonObject.getString("text");
                String sentBy = jsonObject.getString("sentBy");
                messageList.add(new Message(text, sentBy));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
