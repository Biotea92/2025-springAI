package com.example.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private final ChatClient openaiChatClient;
    private final ChatClient ollamaChatClient;

    public SpringAiBoardGameService(ChatClient openaiChatClient, ChatClient ollamaChatClient) {
        this.openaiChatClient = openaiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @Override
    public Answer askQuestion(Question question) {
        String answerText = openaiChatClient.prompt()
                .user(question.question())
                .call()
                .content();
        return new Answer(answerText);
    }
}
