package com.neutron.youchat_backend.nettyServer;

import com.neutron.youchat_backend.common.GroupManageUtil;
import com.neutron.youchat_backend.entity.Friends;
import com.neutron.youchat_backend.entity.Group;
import com.neutron.youchat_backend.entity.User;
import com.neutron.youchat_backend.filter.LoginFilter;
import com.neutron.youchat_backend.mapper.UserMapper;
import com.neutron.youchat_backend.service.FriendsService;
import com.neutron.youchat_backend.service.GroupService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Vector;

@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private GroupService groupService;

    //private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        String message = msg.text();
        //前端传递消息格式：id-message，id为channelGroup编号
        String[] msgArray = message.split("-");

        String id = msgArray[0];//组号
        String s = msgArray[1];//具体消息

        //获取用户当前群聊对应的channelGroup
        ChannelGroup channelGroup = GroupManageUtil.getChannelGroupById(id);

        Channel channel = ctx.channel();
        String username = channel.attr(AttributeKey.valueOf("username")).get().toString();
        String userId = channel.attr(AttributeKey.valueOf("userId")).get().toString();
        System.out.println("群组大小为：" + channelGroup.size());
        channelGroup.forEach(ch -> {
            //channel为数据发送方
            //ch为数据接收方
            if (ch != channel) {
                //别人发送消息
                ch.writeAndFlush(new TextWebSocketFrame(userId + "-" + username + "-" + s));

            } else {
                //自己发送消息
                ch.writeAndFlush(new TextWebSocketFrame(userId + "-" + username + "-" + s));

            }
        });
        System.out.println("[user: " + username + "] 发送消息：" + s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 已上线"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 已下线"));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        User user = userMapper.queryUserById(LoginFilter.id);
        channel.attr(AttributeKey.valueOf("username")).set(user.getUsername());
        channel.attr(AttributeKey.valueOf("userId")).set(user.getId());
        //channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+user.getUsername()+"] 加入群聊"));

        //查找用户建立的所有私聊组
        List<Friends> friendsList = friendsService.queryFriendsByUserId(user.getId());
        Vector<String> vector = new Vector<>();
        for (Friends friends : friendsList) {
            Integer id = friends.getId();
            String sid = "s"+id;
            vector.add(sid);
        }

        //查找用户建立的所有群聊组
        List<Group> groupList = groupService.queryAllGroupsUserJoined(user.getId());
        for (Group group : groupList) {
            Integer id = group.getId();
            String gid = "g"+id;
            vector.add(gid);
        }
        //将用户channel加入组中
        ChannelGroup channelGroup;
        for (String s : vector) {
            channelGroup = GroupManageUtil.existChannelGroupId(s);
            if (channelGroup != null){
                channelGroup.add(channel);
            }
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //Channel channel = ctx.channel();
        //channelGroup.writeAndFlush(new TextWebSocketFrame("[用户："+channel.remoteAddress()+"] 退出群聊"));
    }
}
