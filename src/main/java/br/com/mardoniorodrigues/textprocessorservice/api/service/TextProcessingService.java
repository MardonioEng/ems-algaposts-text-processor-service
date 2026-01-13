package br.com.mardoniorodrigues.textprocessorservice.api.service;

import br.com.mardoniorodrigues.textprocessorservice.api.model.PostProcessingRequest;
import br.com.mardoniorodrigues.textprocessorservice.api.model.PostProcessingResult;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

import static br.com.mardoniorodrigues.textprocessorservice.infrastructure.rabbitmq.RabbitConfig.RESULT_QUEUE;

@Service
public class TextProcessingService {

    private final AmqpTemplate amqpTemplate;

    public TextProcessingService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void receivePost(PostProcessingRequest request) {
        int words = countWords(request.getPostBody());
        BigDecimal value = new BigDecimal(words * 0.10);

        PostProcessingResult result = new PostProcessingResult(request.getPostId(), words, value);
        amqpTemplate.convertAndSend(RESULT_QUEUE, result);
    }

    private int countWords(String body) {
        return (int) Arrays.stream(body.trim().split("\\s+")).count();
    }
}
