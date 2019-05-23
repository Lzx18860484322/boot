package org.springblade.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Created by Shawn Wong on 2017/3/30.
 * Try you best !
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry config) {
//		//发送消息前缀
//		config.enableSimpleBroker("/topic", "/user");
//		//获取消息前缀
//		config.setApplicationDestinationPrefixes("/msg");
//		//发送消息前缀
//		config.setUserDestinationPrefix("/user/");
//	}
//
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/webServer").
//			setHandshakeHandler(new UUIDhandshakeHandler()).setAllowedOrigins("*").withSockJS();
//	}


	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket-endpoint").setHandshakeHandler(new UUIDhandshakeHandler()).setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/global-message");
		registry.setApplicationDestinationPrefixes("/app-receive");

		//发送消息前缀
//		config.enableSimpleBroker("/topic", "/user");
		//获取消息前缀
//		config.setApplicationDestinationPrefixes("/msg");
		//发送消息前缀
//		config.setUserDestinationPrefix("/user/");



	}


}
