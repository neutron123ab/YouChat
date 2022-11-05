package com.neutron.youchat_backend.nettyServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if(ch != channel){
                ch.writeAndFlush(new TextWebSocketFrame("[用户："+ctx.channel().remoteAddress() +"] 发送消息："+ msg.text()));
            } else{
                ch.writeAndFlush(new TextWebSocketFrame("[自己："+ctx.channel().remoteAddress() +"] 发送消息："+ msg.text()));
            }
        });
        System.out.println("[user: "+ctx.channel().remoteAddress() + "] 发送消息：" + msg.text());
        //ctx.channel().writeAndFlush(new TextWebSocketFrame("用户"+ctx.channel().remoteAddress() +"发送消息："+ msg.text()));
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
        channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 加入群聊"));
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 退出群聊"));
    }
}
