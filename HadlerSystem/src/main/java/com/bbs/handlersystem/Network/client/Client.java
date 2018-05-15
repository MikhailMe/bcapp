package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Config.Config;
import com.bbs.handlersystem.Network.Helpers.RequestWrapper;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Network.Message.JsonMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.NonNull;

public final class Client implements Runnable {

    @NonNull
    private Channel channel;

    @NonNull
    private EventLoopGroup group;

    @NonNull
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
    }

    // TODO: i think is not good solution
    public void sendUserAddMessage(String name, String mobileNumber) throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(getUserAddMessage(name, mobileNumber));
    }

    // TODO: i think is not good solution
    public void sendRequestClientInfoMessage() throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(getRequestClientInfo());
    }

    // TODO: i think is not good solution
    public void sendRequestListOfGamesMessage() throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(getListOfGames());
    }

    // for adding user to database
    private String getUserAddMessage(String name, String mobileNumber) {
        User user = new User(name, mobileNumber);
        JsonMessage jm = new JsonMessage<>(user, MessageType.MSG_ADD_USER);
        return jm.toJson();
    }

    // for get client information (name, balance, listOfBets, listOfVisits, currentVisit)
    private String getRequestClientInfo() {
        RequestWrapper requestWrapper = new RequestWrapper("get client info");
        JsonMessage jm = new JsonMessage<>(requestWrapper, MessageType.MSG_REQUEST_CLIENT_INFO);
        return jm.toJson();
    }

    // for get list lis of games
    private String getListOfGames() {
        RequestWrapper request = new RequestWrapper("get list of games");
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
