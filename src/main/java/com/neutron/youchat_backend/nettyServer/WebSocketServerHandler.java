package com.neutron.youchat_backend.nettyServer;

import cn.hutool.log.Log;
import com.neutron.youchat_backend.entity.User;
import com.neutron.youchat_backend.filter.LoginFilter;
import com.neutron.youchat_backend.mapper.UserMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;

@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private UserMapper userMapper;

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        Channel channel = ctx.channel();
        String username = channel.attr(AttributeKey.valueOf("username")).get().toString();
        System.out.println("群组大小为："+channelGroup.size());
        channelGroup.forEach(ch -> {
            //channel为数据发送方
            //ch为数据接收方
            if(ch != channel){
                ch.writeAndFlush(new TextWebSocketFrame("[用户："+username +"] 发送消息："+ msg.text()));
            } else{
                ch.writeAndFlush(new TextWebSocketFrame("[自己："+ username +"] 发送消息："+ msg.text()));
            }
        });
        System.out.println("[user: "+username + "] 发送消息：" + msg.text());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 已上线"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 已下线"));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        User user = userMapper.queryUserById(LoginFilter.id);
        channel.attr(AttributeKey.valueOf("username")).set(user.getUsername());
        channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+user.getUsername()+"] 加入群聊"));
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 退出群聊"));
    }
}
