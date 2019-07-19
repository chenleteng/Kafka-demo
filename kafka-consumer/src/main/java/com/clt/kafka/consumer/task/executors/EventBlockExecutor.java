package com.clt.kafka.consumer.task.executors;

import com.clt.kafka.consumer.dao.SuUserInfoRepository;
import com.clt.kafka.consumer.server.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Component
public class EventBlockExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EventService eventService;

//    @Resource
//    private ParamVerifier paramVerifier;

    @Resource
    private SuUserInfoRepository suUserInfoRepository;

    //这个开多了，要考虑数据库连接数和数据库的QPS是否超过限制（要考虑是否qps高了会导致其他业务受影响）
    private final static int MAX_POOL_SIZE = 20;
    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(MAX_POOL_SIZE);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.HOURS, queue, new ThreadPoolExecutor.AbortPolicy());

    private long historyMax = 0;
    private long debugTime = 0;

    private boolean isFull() {
        return queue.size() >= MAX_POOL_SIZE - 1;
    }

    private void trace() {
        int size = queue.size();
        if (size > historyMax) historyMax = size;
        long now = System.currentTimeMillis();
        if (now - debugTime > 200) {
            debugTime = now;
            logger.info("Executor Queue Size={},historyMax={}", size, historyMax);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            //
        }
    }

    public void doEvent(String data, String topic) {

        while (isFull()) {
            sleep();
        }

        trace();

        boolean reject;
        do {
            reject = false;
            try {
//                executor.execute(new EventWorker(paramVerifier,eventService,suUserInfoRepository,data));
            } catch (RejectedExecutionException e) {
                logger.error("满啦满啦。{}", e.getMessage());
                reject = true;
                while (isFull()) {
                    sleep();
                }
            }
        } while (reject);

    }

}
