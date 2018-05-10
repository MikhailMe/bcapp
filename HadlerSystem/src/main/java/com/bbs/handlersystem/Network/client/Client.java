package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Config.Config;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Utils.JsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public final class Client implements Runnable {

    private Channel channel;
    private EventLoopGroup group;
    private Bootstrap clientBootstrap;

    @Override
    public void run() {
        group = new NioEventLoopGroup();
        clientBootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100);
    }

    // TODO : how close client ??????
    public void closeClient() {
        closeChannel(channel);
        group.shutdownGracefully();
    }

    // TODO: i think is not good solution
    public void sendMessage() {
        channel = openChannel();
        channel.writeAndFlush(getMessage());
    }

    private String getMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("write name: ");
        String name = scanner.next();
        System.out.println("write mobile number: ");
        String mobile = scanner.next();
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        User user = new User(name, mobile);
        JsonMessage jm = new JsonMessage(user, MessageType.MSG_ADD_USER);
        return builder.toJson(jm);

    }

    private Channel openChannel() {
        Channel channel = null;
        try {
            channel = clientBootstrap.connect(Config.HOST, Config.PORT).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channel;
    }

    private void closeChannel(Channel channel) {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
