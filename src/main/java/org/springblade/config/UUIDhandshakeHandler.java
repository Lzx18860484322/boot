package org.springblade.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Shawn Wong on 2017/3/30.
 * Try you best !
 * This handler privide a UUID Principal when it is not present in request
 */
public class UUIDhandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if(request.getPrincipal()==null){
            Principal p = new Principal() {
                private String name = UUID.randomUUID().toString();

                @Override
                public boolean equals(Object another) {
                    if(!(another instanceof Principal)){
                        return false;
                    }else{
                        return this.name.equals(((Principal) another).getName());
                    }


                }

                @Override
                public String toString() {
                    return name;
                }

                @Override
                public int hashCode() {
                    return name.hashCode();
                }

                @Override
                public String getName() {
                    return name;
                }
            };
            return  p;

        }

        return request.getPrincipal();


    }
}
