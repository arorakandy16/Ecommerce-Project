package com.project.ecommerce.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:application.properties")
public class RabbitMQConfiguration {

    @Value("${queue.name}")
    private String queueName;

    @Value("${exchange}")
    private String exchange;

    @Value("${routing.key}")
    private String routingkey;



//    @Bean
//    public Binding bindingDirectExchangeQueueADirect1(DirectExchange directExchange, Queue queueA) {
//        return BindingBuilder.bind(queueA).to(directExchange).with(direct1RoutingKey);
//    }
//
//
//    @Bean
//    public Binding bindingDirectExchangeQueueBDirect2(DirectExchange directExchange, Queue queueB) {
//        return BindingBuilder.bind(queueB).to(directExchange).with(direct2RoutingKey);
//    }
//
//
//    @Value("${routing.topic.rabbitmq.#}")
//    private String topicRabbitMQRoutingKey;
//
//
//    @Bean
//    public Binding bindingTopicExchangeQueueCTopicRabbitMQ(TopicExchange topicExchange, Queue queueC) {
//        return BindingBuilder.bind(queueC).to(topicExchange).with(topicRabbitMQRoutingKey);
//    }
//
//
//    @Value("${routing.topic.rabbitmq.spring.#}")
//    private String topicRabbitMQSpringRoutingKey;
//
//
//    @Bean
//    public Binding bindingTopicExchangeQueueDTopicRabbitMQSpring(TopicExchange topicExchange, Queue queueD) {
//        return BindingBuilder.bind(queueD).to(topicExchange).with(topicRabbitMQSpringRoutingKey);
//    }
//
//
//    @Bean
//    public Binding bindingFanoutExchangeQueueEFanout(FanoutExchange fanoutExchange, Queue queueE) {
//        return BindingBuilder.bind(queueE).to(fanoutExchange);
//    }
//
//
//    @Bean
//    public Binding bindingFanoutExchangeQueueFFanout(FanoutExchange fanoutExchange, Queue queueF) {
//        return BindingBuilder.bind(queueF).to(fanoutExchange);
//    }


    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }


    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }


    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new
                CachingConnectionFactory("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(JMSReceiver jmsReceiver) {
        return new MessageListenerAdapter(jmsReceiver, "receiveMessage");
    }


}
