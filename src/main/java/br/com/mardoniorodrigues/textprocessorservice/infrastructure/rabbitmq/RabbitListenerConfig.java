package br.com.mardoniorodrigues.textprocessorservice.infrastructure.rabbitmq;

import br.com.mardoniorodrigues.textprocessorservice.api.model.PostProcessingRequest;
import br.com.mardoniorodrigues.textprocessorservice.api.service.TextProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitListenerConfig {

    private final TextProcessingService textProcessingService;

    @RabbitListener(queues = RabbitConfig.POST_QUEUE)
    public void consumeProcessing(@Payload PostProcessingRequest request) {
        textProcessingService.receivePost(request);
    }

}
