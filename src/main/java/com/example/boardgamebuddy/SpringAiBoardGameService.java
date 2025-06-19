package com.example.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

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
        Flux<String> content = openaiChatClient.prompt()
                .user(question.question())
                .stream()
                .content();
        String answer = content.collectList().block().stream().collect(Collectors.joining());
        return new Answer(answer);
    }
}
