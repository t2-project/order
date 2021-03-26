package de.unistuttgart.t2.order.saga;

import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.commands.order.OrderAction;
import de.unistuttgart.t2.common.commands.order.OrderCommand;
import de.unistuttgart.t2.common.commands.order.OrderCompensation;
import de.unistuttgart.t2.common.replies.OrderCreated;
import de.unistuttgart.t2.order.OrderService;
import io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;



public class OrderCommandHandler {

	@Autowired
	private OrderService orderService;
	
	public CommandHandlers commandHandlers() {
		return SagaCommandHandlersBuilder.fromChannel(OrderCommand.channel)
				.onMessage(OrderAction.class, this::createOrder)
				.onMessage(OrderCompensation.class, this::rejectOrder)
				.build();
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public Message createOrder(CommandMessage<OrderAction> cm) {
		OrderAction cmd = cm.getCommand();
		
		String orderId = orderService.createOrder(cmd.getSessionId());
		OrderCreated reply = new OrderCreated(orderId);
		return CommandHandlerReplyBuilder.withSuccess(reply);
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public Message rejectOrder(CommandMessage<OrderCompensation> cm) {
		OrderCompensation cmd = cm.getCommand();

		orderService.rejectOrder(cmd.getOrderId());
		return CommandHandlerReplyBuilder.withSuccess();
	}
}
