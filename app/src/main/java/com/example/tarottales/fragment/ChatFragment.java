package com.example.tarottales.fragment;

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
import android.widget.TextView;

import com.example.tarottales.Model.Message;
import com.example.tarottales.Model.MessageAdapter;
import com.example.tarottales.R;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.ArrayList;


public class ChatFragment extends Fragment {

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

        // Setup RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
    }

    private void addToChat(String message, String sentBy) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    private void addResponse(String response) {
        messageList.remove(messageList.size() - 1); // Remove "Typing..." message
        addToChat(response, Message.SENT_BY_BOT);
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
                    addResponse(resultText.trim());
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
}
