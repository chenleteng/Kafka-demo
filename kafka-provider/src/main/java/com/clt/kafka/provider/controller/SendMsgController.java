package com.clt.kafka.provider.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clt.kafka.provider.service.KafkaSenderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMsgController {

    @Resource
    private KafkaSenderService kafkaSenderService;

    @RequestMapping(value = "/send",method = RequestMethod.GET)
    public String send(String msg){
        JSONObject json = JSON.parseObject(msg);
        kafkaSenderService.sendEvent(json);
        return "success";
    }

}
