package com.rota.facil.auth_service.messaging.consumers;

import com.rota.facil.auth_service.business.user.DecreaseUserTripsUseCase;
import com.rota.facil.auth_service.business.user.IncreaseUserCompletedTripsUseCase;
import com.rota.facil.auth_service.business.user.IncreaseUserTripsUseCase;
import com.rota.facil.auth_service.business.user.UpdateUserScoreUseCase;
import com.rota.facil.auth_service.messaging.dto.receive.user.UserCompleteTripEventReceive;
import com.rota.facil.auth_service.messaging.dto.receive.user.UserUpdateScoreEventReceive;
import com.rota.facil.auth_service.messaging.dto.receive.user.UserUpdateTripsEventReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitTransportUserEventConsumer {
    private final UpdateUserScoreUseCase updateUserScoreUseCase;
    private final IncreaseUserTripsUseCase increaseUserTripsUseCase;
    private final IncreaseUserCompletedTripsUseCase increaseUserCompletedTripsUseCase;
    private final DecreaseUserTripsUseCase decreaseUserTripsUseCase;

    @RabbitListener(queues = "${rabbitmq.auth.user.updated.queue}")
    public void handlerUpdateScore(UserUpdateScoreEventReceive event) {
        updateUserScoreUseCase.execute(event.userId(), event.note());
    }

    @RabbitListener(queues = "${rabbitmq.auth.user.complete.trip.queue}")
    public void handlerUserCompleteTrip(UserCompleteTripEventReceive event) {
        increaseUserCompletedTripsUseCase.execute(event.userIds());
    }

    @RabbitListener(queues = "${rabbitmq.auth.user.trips.increased.queue}")
    public void handlerUserTripsIncreased(UserUpdateTripsEventReceive event) {
        increaseUserTripsUseCase.execute(event.userIds());
    }

    @RabbitListener(queues = "${rabbitmq.auth.user.trips.decreased.queue}")
    public void handlerUserTripsDecreased(UserUpdateTripsEventReceive event) {
        decreaseUserTripsUseCase.execute(event.userIds());
    }
}
