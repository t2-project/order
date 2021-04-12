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
import de.unistuttgart.t2.repository.OrderStatus;
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
				.onMessage(CompensationCommand.class, this::rejectOrder).build();
	}

	/**
	 * create a new order. 
	 * 
	 * @param cm
	 * @return id of the newly created order.
	 */
	public Message createOrder(CommandMessage<ActionCommand> cm) {
		LOG.info("order received action");
		ActionCommand cmd = cm.getCommand();
		SagaData data = cmd.getData();

		try {
			String orderId = orderService.createOrder(data.getSessionId());
			OrderCreatedReply reply = new OrderCreatedReply(orderId);
			return CommandHandlerReplyBuilder.withSuccess(reply);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			
		}
		return CommandHandlerReplyBuilder.withFailure();
	}

	/**
	 * set an orders status to {@linkplain OrderStatus.FAILURE}
	 * 
	 * if the given orderId does not match any order in the repository, ignore this.
	 * rationale: if the content of the command is wrong, then this will fail every
	 * time the order service tries to consume the message.
	 * 
	 * if the rejection fails due to any other problem do not ignore. because this
	 * might fix itself on a retry
	 * 
	 * @param cm
	 * @return
	 */
	public Message rejectOrder(CommandMessage<CompensationCommand> cm) {
		LOG.info("order received  compensation");
		CompensationCommand cmd = cm.getCommand();
		SagaData data = cmd.getData();

		try {
			orderService.rejectOrder(data.getOrderId());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return CommandHandlerReplyBuilder.withSuccess();
	}
}
