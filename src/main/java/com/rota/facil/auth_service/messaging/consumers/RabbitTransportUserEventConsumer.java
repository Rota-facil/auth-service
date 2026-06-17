package com.rota.facil.auth_service.messaging.consumers;

import com.rota.facil.auth_service.business.UserService;
import com.rota.facil.auth_service.messaging.dto.receive.user.UserCompleteTripEventReceive;
import com.rota.facil.auth_service.messaging.dto.receive.user.UserUpdateScoreEventReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitTransportUserEventConsumer {
    private final UserService userService;

    @RabbitListener(queues = "${rabbitmq.auth.user.updated.queue}")
    public void handlerUpdateScore(UserUpdateScoreEventReceive event) {
        userService.updateScore(event.userId(), event.note());
    }

    @RabbitListener(queues = "${rabbitmq.auth.user.complete.trip.queue}")
    public void handlerUserCompleteTrip(UserCompleteTripEventReceive event) {
        userService.increaseTripCompleted(event.userIds());
    }
}
