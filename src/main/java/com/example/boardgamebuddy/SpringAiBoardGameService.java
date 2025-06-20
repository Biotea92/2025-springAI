package com.example.boardgamebuddy;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
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
    public Answer askQuestion(AskQuestion askQuestion) {
        Flux<String> content = openaiChatClient.prompt()
                .advisors(new SimpleLoggerAdvisor())
                .user(askQuestion.askQuestion())
                .stream()
                .content();
        String answer = content.collectList().block().stream().collect(Collectors.joining());
        return new Answer(answer);
    }
}
