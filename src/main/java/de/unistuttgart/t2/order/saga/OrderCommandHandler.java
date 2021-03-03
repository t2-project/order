package de.unistuttgart.t2.order.saga;

import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.common.commands.CreateOrderCommand;
import de.unistuttgart.t2.common.commands.OrderCommand;
import de.unistuttgart.t2.common.commands.RejectOrderCommand;
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
				.onMessage(CreateOrderCommand.class, this::createOrder)
				.onMessage(RejectOrderCommand.class, this::rejectOrder)
				.build();
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public Message createOrder(CommandMessage<CreateOrderCommand> cm) {
		CreateOrderCommand cmd = cm.getCommand();
		
		//TODO logic with failures
		orderService.createOrder(cmd.getProductId(), cmd.getAmount(), cmd.getTotal());
		return CommandHandlerReplyBuilder.withSuccess();
	}
	
	/**
	 * 
	 * @param cm
	 * @return
	 */
	public Message rejectOrder(CommandMessage<RejectOrderCommand> cm) {
		RejectOrderCommand cmd = cm.getCommand();
		
		//TODO logic with failures
		orderService.rejectOrder(cmd.getOrderId());
		return CommandHandlerReplyBuilder.withSuccess();
	}
}
