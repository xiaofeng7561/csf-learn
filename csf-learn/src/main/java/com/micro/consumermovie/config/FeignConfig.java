package com.micro.consumermovie.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;

/**
 * @author AFeng
 * @date 2019/10/29 9:57
 */
public class FeignConfig {

    @Bean
    public Contract feignContract(){
       return  new Contract.Default();
    }





}
