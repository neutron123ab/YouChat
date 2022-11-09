package com.neutron.youchat_backend.common;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManageUtil {

    private static final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    /**
     * 获取保存了所有channelGroup的map集合
     * @return map
     */
    public static Map<String, ChannelGroup> getChannelGroupMap(){
        return channelGroupMap;
    }

    /**
     * 添加群组
     * @param id 组号
     */
    public static void addChannelGroup(String id){
        ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        channelGroupMap.put(id, channelGroup);
    }

    /**
     * 根据id获取指定的channelGroup
     * @param id 组id
     * @return 对应的channelGroup
     */
    public static ChannelGroup getChannelGroupById(String id){
        return channelGroupMap.get(id);
    }

    /**
     * 判断当前map中是否存在该组id
     * @param id 组id
     * @return 如果存在就返回对应的channelGroup
     */
    public static ChannelGroup existChannelGroupId(String id){
        for (Map.Entry<String, ChannelGroup> entry : channelGroupMap.entrySet()) {
            if (entry.getKey().equals(id)){
                return entry.getValue();
            }
        }
        return null;
    }
}
