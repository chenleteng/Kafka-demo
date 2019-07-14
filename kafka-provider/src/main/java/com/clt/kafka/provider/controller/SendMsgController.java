package com.clt.kafka.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {

    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public String send(String msg){

        return "success";
    }

}
