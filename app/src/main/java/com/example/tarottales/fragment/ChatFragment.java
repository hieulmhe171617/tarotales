package com.example.tarottales.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tarottales.R;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String apiKey = "AIzaSyA4mTa5P9V8QtEVOPecbktLdd6LJ5umvHI"; // Thay thế bằng API Key thực tế của bạn
    private TextView textView;
    private EditText editTextInput;
    private Button buttonCallGeminiAI;
    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        textView = view.findViewById(R.id.textView);
        editTextInput = view.findViewById(R.id.editTextInput);

        buttonCallGeminiAI = view.findViewById(R.id.buttonCallGeminiAI);
        buttonCallGeminiAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallGeminiAI();
            }
        });
    }

    public void buttonCallGeminiAI() {
        // Lấy câu hỏi từ EditText
        String userInput = editTextInput.getText().toString().trim();
        if (userInput.isEmpty()) {
            textView.setText("Vui lòng nhập câu hỏi.");
            return;
        }
        // Chỉ định mô hình Gemini phù hợp với trường hợp sử dụng của bạn
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        // Tạo nội dung để gửi đến AI
        Content content = new Content.Builder()
                .addText(userInput)
                .build();

        // Tạo một executor để xử lý kết quả
        Executor executor = Executors.newSingleThreadExecutor();

        // Gọi API để tạo nội dung
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        // Kiểm tra kết quả
                        if (result != null) {
                            String resultText = result.getText();
                            textView.setText(resultText);
                        } else {
                            textView.setText("No response received.");
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        textView.setText("Lỗi: " + t.getMessage());
                        t.printStackTrace();
                    }
                },
                executor);
    }

}