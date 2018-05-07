package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Network.Encoding.Decoder;
import com.bbs.handlersystem.Network.Encoding.Encoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        System.out.println("New client connected: " + ch.localAddress());
        ChannelPipeline p = ch.pipeline();
        p.addLast(new Decoder());
        p.addLast(new Encoder());
        p.addLast(new NettyServerHandler());
    }
}
