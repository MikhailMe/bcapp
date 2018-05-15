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
    public void sendMessage(String name, String mobileNumber) throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(getUserAddMessage(name, mobileNumber));
    }

    // for adding user to database
    private String getUserAddMessage(String name, String mobileNumber) {
        User user = new User(name, mobileNumber);
        JsonMessage jm = new JsonMessage<>(user, MessageType.MSG_ADD_USER);
        return jm.toJson();
    }

    // for get
    private String getRequestClientInfo() {
        RequestWrapper requestWrapper = new RequestWrapper("");
        JsonMessage jm = new JsonMessage<>(requestWrapper, MessageType.MSG_REQUEST_CLIENT_INFO);
        return jm.toJson();
    }

    // for get list lis of games
    private String getListOfGames() {
        String string = "get list of games";
        RequestWrapper request = new RequestWrapper(string);
        JsonMessage jm = new JsonMessage<>(request, MessageType.MSG_REQUEST_LIST_OF_GAMES);
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
