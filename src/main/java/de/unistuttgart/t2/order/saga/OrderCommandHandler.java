package de.unistuttgart.t2.order.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.commands.ActionCommand;
import de.unistuttgart.t2.common.commands.CompensationCommand;
import de.unistuttgart.t2.common.commands.SagaCommand;
import de.unistuttgart.t2.common.domain.saga.SagaData;
import de.unistuttgart.t2.common.replies.OrderCreated;
import de.unistuttgart.t2.order.OrderService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;



public class OrderCommandHandler {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OrderService orderService;
	
	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(SagaCommand.order)
				.onMessage(ActionCommand.class, this::createOrder)
				.onMessage(CompensationCommand.class, this::rejectOrder)
				.build();
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public Message createOrder(CommandMessage<ActionCommand> cm) {
		LOG.info("order received action");
		ActionCommand cmd = cm.getCommand();
		SagaData data = cmd.getData();
		
		String orderId = orderService.createOrder(data.getSessionId());
		
		OrderCreated reply = new OrderCreated(orderId);
		return CommandHandlerReplyBuilder.withSuccess(reply);
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public Message rejectOrder(CommandMessage<CompensationCommand> cm) {
		LOG.info("order received  compensation");
		CompensationCommand cmd = cm.getCommand();
		SagaData data = cmd.getData();

		orderService.rejectOrder(data.getOrderId());
		return CommandHandlerReplyBuilder.withSuccess();
	}
}
