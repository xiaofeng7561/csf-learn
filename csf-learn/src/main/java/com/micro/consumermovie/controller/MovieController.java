package com.micro.consumermovie.controller;

import com.micro.consumermovie.service.api.UserApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author AFeng
 * @date 2019/10/17 15:26
 */
@RestController
@RequestMapping("api/v1")
public class MovieController {

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    private final LoadBalancerClient loadBalancerClient;

    private final UserApi userApi;

    private final RedisTemplate redisTemplate;


    @Autowired
    public MovieController(RestTemplate restTemplate, DiscoveryClient discoveryClient, LoadBalancerClient loadBalancerClient, UserApi userApi, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.loadBalancerClient = loadBalancerClient;
        this.userApi = userApi;
        this.redisTemplate = redisTemplate;
    }



    @GetMapping("user/{username}")
    public String getUser(@PathVariable("username") String username){
        Object stock = redisTemplate.opsForValue().get("stock");
        System.out.println(stock);
        return userApi.findUser(username);
    }

    @HystrixCommand(fallbackMethod = "getUser2Fallback",ignoreExceptions = Exception.class)
    @GetMapping("user2/{username}")
    public String getUser2(@PathVariable("username") String username){
        return restTemplate.getForObject("http://use/micro-provider/api/user/"+username,String.class);
    }


    public String getUser2Fallback(String id){
        System.out.println(id);
        return "11";
    }


    @GetMapping("getEurekaInfo")
    public List<String> getEurekaInfo(){

        List<String> services = discoveryClient.getServices();
        return services;

    }

    @GetMapping("loadBalanceGet")
    public void loadBalanceGet(){
        ServiceInstance user = loadBalancerClient.choose("user");
        System.out.println(user.getPort());
    }


}
