package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Network.Encoding.Decoder;
import com.bbs.handlersystem.Network.Encoding.Encoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        System.out.println("New client connected: " + socketChannel.localAddress());
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        //pipeline.addLast(new Decoder());
        //pipeline.addLast(new Encoder());
        pipeline.addLast(new NettyServerHandler());
    }
}