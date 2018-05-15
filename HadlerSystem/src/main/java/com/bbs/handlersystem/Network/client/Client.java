package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Config.Config;
import com.bbs.handlersystem.Network.Helpers.RequestWrapper;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Network.Message.JsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public final class Client implements Runnable {

    private Gson builder;
    private Channel channel;
    private EventLoopGroup group;
    private Bootstrap clientBootstrap;

    @Override
    public void run() {
        builder = new GsonBuilder().setPrettyPrinting().create();
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
    }

    // TODO: i think is not good solution
    public void sendMessage() throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(getRequestClientInfo());
    }

    private String getUserAddMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("write name: ");
        String name = scanner.next();
        System.out.println("write mobile number: ");
        String mobile = scanner.next();
        User user = new User(name, mobile);
        JsonMessage jm = new JsonMessage<>(user, MessageType.MSG_ADD_USER);
        return jm.toJson();
    }

    private String getRequestClientInfo() {
        RequestWrapper requestWrapper = new RequestWrapper("");
        JsonMessage jm = new JsonMessage<>(requestWrapper, MessageType.MSG_REQUEST_CLIENT_INFO);
        return jm.toJson();
    }

    private String getRequestInfoMessage() {
        String string = "get list of games";
        RequestWrapper request = new RequestWrapper(string);
        JsonMessage jm = new JsonMessage<>(request, MessageType.MSG_REQUEST_CLIENT_INFO);
        return jm.toJson();
    }

    private Channel openChannel() throws InterruptedException {
        return clientBootstrap.connect(Config.HOST, Config.PORT).sync().channel();
    }

    private void closeChannel(Channel channel) {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
