package com.micro.consumermovie.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.consumermovie.config.FeignConfig;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author AFeng
 * @date 2019/10/23 17:50
 */
@FeignClient(name = "user", configuration = FeignConfig.class,fallbackFactory = UserApi.UserApiFallbackFactory.class)
public interface UserApi {

    @RequestLine("GET  micro-provider/api/user/{id}")
    String findUser(@Param("id") String id);



    @Component
    class UserApiFallbackFactory implements FallbackFactory<UserApi>{

        @Override
        public UserApi create(Throwable throwable) {
            return new UserApi() {
                @Override
                public String findUser(String id)  {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String s = null;
                    try {

                        s = objectMapper.writeValueAsString(throwable);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.println(s);
                    return "";
                }
            };
        }
    }




}
