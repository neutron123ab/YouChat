package com.neutron.youchat_backend.nettyServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.net.ssl.SSLEngine;

@Component
public class SecureWebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private WebSocketServerHandler webSocketServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        SelfSignedCertificate certificate = new SelfSignedCertificate();
        SslContext context = SslContext.newServerContext(certificate.certificate(), certificate.privateKey());
        SSLEngine engine = context.newEngine(pipeline.channel().alloc());
        engine.setUseClientMode(false);
        pipeline.addFirst(new SslHandler(engine));

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
        pipeline.addLast(webSocketServerHandler);
    }
}
