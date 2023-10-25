package com.allnote.openAi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final RestTemplate template;
    @Value("${application.openai.api.model}")
    private String model;
    @Value(("${application.openai.api.url}"))
    private String apiURL;

    public String generateSummaryByChatGpt(String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }

}
