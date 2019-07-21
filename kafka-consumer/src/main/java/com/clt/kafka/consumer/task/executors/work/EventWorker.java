package com.clt.kafka.consumer.task.executors.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clt.kafka.consumer.dao.TbItemRepository;
import com.clt.kafka.consumer.entity.SuUserInfo;
import com.clt.kafka.consumer.entity.TbItem;
import com.clt.kafka.consumer.log.LogFactory;
import com.clt.kafka.consumer.server.EventService;
import com.clt.kafka.consumer.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class EventWorker implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Logger loginLogger = LogFactory.getLogger(LogFactory.LOGGER.UserLoginLogger);
    private final Logger registerLogger = LogFactory.getLogger(LogFactory.LOGGER.UserRegisterLogger);

    private TbItemRepository tbItemRepository;
    private EventService eventService;
    private String data;

    public EventWorker(EventService eventService, TbItemRepository tbItemRepository, String data) {
        this.tbItemRepository = tbItemRepository;
        this.eventService = eventService;
        this.data = data;
    }

    @Override
    public void run() {

        if (eventService.exists(data)) {
            logger.debug("已经处理过的数据。");
            return;
        }

        JSONObject jo = JSON.parseObject(data);
        TbItem item = new TbItem();
        item.setTitle(jo.getString("title"));
        item.setSellPoint(jo.getString("sellPoint"));
        item.setPrice(jo.getLong("price"));
        item.setNum(jo.getInteger("num"));
        item.setBarcode(jo.getString("barcode"));
        item.setImage(jo.getString("image"));
        item.setCid(jo.getLong("cid"));
        item.setStatus(jo.getInteger("status"));
        item.setCreated(new Date());
        item.setUpdated(new Date());

        tbItemRepository.saveAndFlush(item);


        logger.info("处理业务逻辑 >>> "+jo.toJSONString());

    }
}
