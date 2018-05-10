package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Database.Store;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private Store store;

    NettyServerInitializer(Store store) {
        this.store = store;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        System.out.println("New client connected: " + socketChannel.localAddress());
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new NettyServerHandler(store));
    }
}
