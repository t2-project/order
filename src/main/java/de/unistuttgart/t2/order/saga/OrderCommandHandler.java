package de.unistuttgart.t2.order.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.saga.OrderCreatedReply;
import de.unistuttgart.t2.common.saga.SagaData;
import de.unistuttgart.t2.common.saga.commands.ActionCommand;
import de.unistuttgart.t2.common.saga.commands.CompensationCommand;
import de.unistuttgart.t2.common.saga.commands.SagaCommand;
import de.unistuttgart.t2.order.OrderService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

/**
 * handles messages for the order service.
 * 
 * listens to the {@code order} queue.
 * 
 * creates a new order upon receiving a
 * {@link de.unistuttgart.t2.common.saga.commands.ActionCommand ActionCommand} or rejects an
 * existing order upon receiving a
 * {@link de.unistuttgart.t2.common.saga.commands.CompensationCommand CompensationCommand}.
 * 
 * @author stiesssh
 *
 */
public class OrderCommandHandler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;

    public CommandHandlers commandHandlers() {
        return SagaCommandHandlersBuilder.fromChannel(SagaCommand.order)
                .onMessage(ActionCommand.class, this::createOrder)
                .onMessage(CompensationCommand.class, this::rejectOrder).build();
    }

    /**
     * create a new order.
     * 
     * @param cm the command message
     * @return the reply message. if successful it contains the id of the created order.
     */
    public Message createOrder(CommandMessage<ActionCommand> cm) {
        LOG.info("order received action");
        ActionCommand cmd = cm.getCommand();
        SagaData data = cmd.getData();

        if (data.getSessionId() == null) {
            LOG.info(String.format("could not process order action with missing session id"));
            return CommandHandlerReplyBuilder.withFailure();
        }

        String orderId = orderService.createOrder(data.getSessionId());
        OrderCreatedReply reply = new OrderCreatedReply(orderId);
        return CommandHandlerReplyBuilder.withSuccess(reply);
    }

    /**
     * reject an existing order. 
     * 
     * @param cm the command message
     * @return the reply message
     */
    public Message rejectOrder(CommandMessage<CompensationCommand> cm) {
        LOG.info("order received  compensation");
        CompensationCommand cmd = cm.getCommand();
        SagaData data = cmd.getData();

        if (data.getSessionId() == null) {
            LOG.info(String.format("could not process order action with missing order id"));
            return CommandHandlerReplyBuilder.withFailure();
        }
        
        orderService.rejectOrder(data.getOrderId());

        return CommandHandlerReplyBuilder.withSuccess();
    }
}
