package com.neutron.youchat_backend.nettyServer;

import com.neutron.youchat_backend.common.GroupManageUtil;
import com.neutron.youchat_backend.entity.*;
import com.neutron.youchat_backend.filter.LoginFilter;
import com.neutron.youchat_backend.mapper.UserMapper;
import com.neutron.youchat_backend.service.FriendsService;
import com.neutron.youchat_backend.service.GroupChatService;
import com.neutron.youchat_backend.service.GroupService;
import com.neutron.youchat_backend.service.SingleChatService;
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

    @Autowired
    private SingleChatService singleChatService;

    @Autowired
    private GroupChatService groupChatService;

    //private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        String message = msg.text();
        //前端传递消息格式：id-message，id为channelGroup编号
        String[] msgArray = message.split("-");

        String id = msgArray[0];//组号，格式为：s(g)id
        String chatId = id.substring(1);//好友表或用户-群聊关联表中的id
        String s = msgArray[1];//具体消息

        //获取用户当前群聊对应的channelGroup
        ChannelGroup channelGroup = GroupManageUtil.getChannelGroupById(id);

        Channel channel = ctx.channel();
        String username = channel.attr(AttributeKey.valueOf("username")).get().toString();
        String userId = channel.attr(AttributeKey.valueOf("userId")).get().toString();
        channelGroup.forEach(ch -> {
            //channel为数据发送方
            //ch为数据接收方
            if (ch != channel) {
                //给别人发送消息
                String username1 = ch.attr(AttributeKey.valueOf("username")).get().toString();
                System.out.println("username1: "+username1);
                ch.writeAndFlush(new TextWebSocketFrame(userId + "-" + username + "-" + s));

            } else {
                //给自己发送消息
                ch.writeAndFlush(new TextWebSocketFrame(userId + "-" + username + "-" + s));
                //私聊
                if(id.charAt(0) == 's'){
                    SingleChat singleChat = new SingleChat();
                    singleChat.setUserFriendsId(Integer.parseInt(chatId));
                    singleChat.setUserId(Integer.parseInt(userId));
                    singleChat.setContent(s);
                    singleChatService.addMsgUserSend(singleChat);
                } else if(id.charAt(0) == 'g'){
                    //群聊
                    GroupChat groupChat = new GroupChat();
                    groupChat.setUserGroupId(Integer.parseInt(chatId));
                    groupChat.setContent(s);
                    groupChatService.addMsgUserSendInGroup(groupChat);
                }
            }
        });
        System.out.println("[user: " + username + "] 发送消息：" + s);
        System.out.println("组号为：" + id);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("websocket出现异常");
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        Channel channel = ctx.channel();
//        String userId = channel.attr(AttributeKey.valueOf("userId")).get().toString();
//        String username = channel.attr(AttributeKey.valueOf("username")).get().toString();
//        //根据用户id获取他加入的所有群组
//        List<Friends> friendsList = friendsService.queryFriendsByUserId(Integer.parseInt(userId));
//        for (Friends friends : friendsList) {
//            String sid = "s" + friends.getId();
//            ChannelGroup channelGroup = GroupManageUtil.getChannelGroupById(sid);
//            channelGroup.writeAndFlush(new TextWebSocketFrame(userId + "-"+username+"-已上线"));
//        }
//
//        List<Group> groupList = groupService.queryAllGroupsUserJoined(Integer.parseInt(userId));
//        for (Group group : groupList) {
//            String gid = "g"+group.getId();
//            ChannelGroup channelGroup = GroupManageUtil.getChannelGroupById(gid);
//            channelGroup.writeAndFlush(new TextWebSocketFrame(userId + "-"+username+"-已上线"));
//        }
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
            if(GroupManageUtil.getChannelGroupById(sid) == null){
                GroupManageUtil.addChannelGroup(sid);
            }
            vector.add(sid);
        }

        //查找用户建立的所有群聊组
        List<Group> groupList = groupService.queryAllGroupsUserJoined(user.getId());
        for (Group group : groupList) {
            Integer id = group.getId();
            String gid = "g"+id;
            if(GroupManageUtil.getChannelGroupById(gid) == null){
                GroupManageUtil.addChannelGroup(gid);
            }
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
