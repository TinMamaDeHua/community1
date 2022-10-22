package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 概要描述：事件的生产者
 * 详细描述：生产者需要通过KafkaTemplate来向Kafka的主题发送数据
 *
 * @author:程圣严 日期：2022-10-18 10:27
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 将触发的事件转化为json字符串发给对应主题
     * 消费者事件通过该字符串可以得到Event对象，就可以对数据进行相应的业务操作
     * @param event
     */
    public void fireEvent(Event event) {
        // 将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
