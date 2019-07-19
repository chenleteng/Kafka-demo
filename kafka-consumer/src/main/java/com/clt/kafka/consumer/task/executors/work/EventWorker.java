package com.clt.kafka.consumer.task.executors.work;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clt.kafka.consumer.dao.SuUserInfoRepository;
import com.clt.kafka.consumer.entity.SuUserInfo;
import com.clt.kafka.consumer.log.LogFactory;
import com.clt.kafka.consumer.server.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class EventWorker implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Logger loginLogger = LogFactory.getLogger(LogFactory.LOGGER.UserLoginLogger);
    private final Logger registerLogger = LogFactory.getLogger(LogFactory.LOGGER.UserRegisterLogger);

    private SuUserInfoRepository suUserInfoRepository;
    private EventService eventService;
    private String data;


    @Override
    public void run() {

//        if (eventService.exists(data)) {
//            logger.debug("已经处理过的数据。");
//            return;
//        }

        JSONObject jo = JSON.parseObject(data);
        SuUserInfo userInfo = new SuUserInfo();

        if ("LOGIN".equals(jo.getString("type"))){
            userInfo = suUserInfoRepository.getFirstByGameIdAndChnlIdAndUserId(jo.getInteger("gameId"), jo.getString("chnlId"), jo.getLong("userId"));
            if (userInfo == null){
                loginLogger.info("用户不存在, index = [idx_chnl_user], gameId = {}, chnlId = {}, userId = {}",
                        jo.getInteger("gameId"), jo.getString("chnlId"), jo.getLong("userId"));
                return;
            }

            if (DateUtil.between(userInfo.getLastLoginTime(), jo.getDate("ctime"), DateUnit.SECOND,false) > 0){
                loginLogger.info(JSON.toJSONString(userInfo));
                userInfo.setLoginCount(userInfo.getLoginCount() + 1);
                userInfo.setLastLoginTime(new Date());
                int i = suUserInfoRepository.updateLoginCountAndTime(userInfo);
            }
        }

        if ("REGISTER_AUTO_SUSDK".equals( jo.getString("type"))){
            userInfo = JSON.parseObject(jo.getString("userInfo"), SuUserInfo.class);

            try {
//                paramVerifier.notNull(userInfo, "数据解析错误");

                if (userInfo.getUserId() != null) userInfo.setUserId(userInfo.getUserId());
                if (userInfo.getChnlId() != null) userInfo.setChnlId(userInfo.getChnlId());
                if (userInfo.getChnlUserId() != null) userInfo.setChnlUserId(userInfo.getChnlUserId());
                if (userInfo.getGameId() != null) userInfo.setGameId(userInfo.getGameId());
                if (userInfo.getDeviceId() != null) userInfo.setDeviceId(userInfo.getDeviceId());
                if (userInfo.getImei() != null) userInfo.setImei(userInfo.getImei());
                if (userInfo.getMac() != null) userInfo.setMac(userInfo.getMac());
                if (userInfo.getModel() != null) userInfo.setModel(userInfo.getModel());
                if (userInfo.getResolution() != null) userInfo.setResolution(userInfo.getResolution());
                DateTime defaultDate = DateUtil.parse("2000-01-01 00:00:00");
                if (defaultDate != null) {
                    userInfo.setActTime(defaultDate);
                    userInfo.setFirstChargeTime(defaultDate);
                }
                userInfo.setRegTime(new Date());
                if (userInfo.getRegIp() != null) userInfo.setRegIp(userInfo.getRegIp());
                userInfo.setFirstLoginTime(new Date());
                userInfo.setLastLoginTime(new Date());
                userInfo.setLoginCount(1);
                userInfo.setAct2(false);
                userInfo.setAct3(false);
                userInfo.setAct4(false);
                userInfo.setAct5(false);
                userInfo.setAct6(false);
                userInfo.setAct7(false);
                userInfo.setAct15(false);
                userInfo.setAct30(false);
                userInfo.setMtime(new Date());

                suUserInfoRepository.saveAndFlush(userInfo);

//                eventService.cache(data);
                registerLogger.info(JSON.toJSONString(userInfo));

            } catch (Exception e) {
                logger.error("保存用户失败, errMsg = {}", e.getMessage());

                SuUserInfo exists = suUserInfoRepository.getFirstByGameIdAndChnlIdAndChnlUserId(userInfo.getGameId(), userInfo.getChnlId(), userInfo.getChnlUserId());
                if (exists != null) {
                    logger.info("保存用户失败, 用户已存在, index = [idx_chnl_user], id = {}, gameId = {}, chnlId = {}, chnlUserId = {}",
                            userInfo.getId(), userInfo.getGameId(), userInfo.getChnlId(), userInfo.getChnlUserId());
                    return;
                }
                exists = suUserInfoRepository.getFirstByGameIdAndChnlIdAndUserId(userInfo.getGameId(), userInfo.getChnlId(), userInfo.getUserId());
                if (exists != null) {
                    logger.info("保存用户失败, 用户已存在, index = [idx_game_user_chnl], id = {}, gameId = {}, userId = {}, chnlId = {}",
                            userInfo.getId(), userInfo.getGameId(), userInfo.getUserId(), userInfo.getChnlId());
                }
            }
        }

    }

}
