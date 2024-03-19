package com.epam.openai.demo;


import com.ejlchina.http.internal.HttpException;
import com.epam.exception.ParamsException;
import com.epam.service.TemperatureQueryService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api")
public class GenerateController {

    @Autowired
    private OpenAiService openAiService;

    // @Limit(key = "test", period = 1, count = 100, name = "testLimit", prefix = "queryTemp")
    @GetMapping(value = "/generate")
    public ResponseEntity generate(String role, String content) {
//        Optional<Integer> optional = null;
        List messages = new ArrayList<ChatMessage>();
        ChatMessage message = new ChatMessage(role, content);
        messages.add(message);

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .n(3)
                .temperature(0.6).stream(true).messages(messages)
                .build();
        ChatCompletionResult result = openAiService.createChatCompletion(completionRequest);

        return new ResponseEntity(result, HttpStatus.OK);


    }
}
