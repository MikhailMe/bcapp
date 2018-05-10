package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Config.Config;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Utils.JsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.Executors;

public final class Client implements Runnable {

    private final ClientHandler clientHandler = new ClientHandler();

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }

    public synchronized void startClient() {
        Executors.newFixedThreadPool(1).execute(this);
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer())
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100);
            ChannelFuture future = bootstrap.connect(Config.HOST, Config.PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void writeMessage(String messageToSend) {
        clientHandler.writeMessage(messageToSend);
    }

}
