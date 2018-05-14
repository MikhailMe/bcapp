package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Config.Config;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Utils.JsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public final class Client implements Runnable {

    private ChannelFuture future;
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
        try {
            clientBootstrap.connect(Config.HOST, Config.PORT).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();
            group.shutdownGracefully();
        }
    }

    // TODO : how close client ??????
    public void closeClient() {
        closeChannel(future.channel());
    }

    // TODO: i think is not good solution
    public void sendMessage() {
        future = openChannel();
        future.channel().writeAndFlush(getRequestInfoMessage());
    }

    private String getUserAddMessage() {
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

    // TODO: make me
    private String getRequestInfoMessage() {
        String request = "get list of games";
        JsonMessage jm = new JsonMessage(request, MessageType.MSG_REQUEST_CLIENT_INFO);
        // FIXME
        return jm.toString();
    }

    private ChannelFuture openChannel() {
        ChannelFuture future = null;
        try {
            future = clientBootstrap.connect(Config.HOST, Config.PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return future;
    }

    private void closeChannel(Channel channel) {
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
