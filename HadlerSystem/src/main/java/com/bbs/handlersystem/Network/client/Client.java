package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Config.Config;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.NonNull;

import java.sql.Timestamp;

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

    public void sendUserAddMessage(@NonNull final String name,
                                   @NonNull final String mobileNumber) throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(MessageSender.getUserAddMessage(name, mobileNumber));
    }

    public void sendRequestClientInfoMessage() throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(MessageSender.getRequestClientInfo());
    }

    public void sendRequestListOfGamesMessage() throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(MessageSender.getListOfGames());
    }

    public void sendBetTransactionMessage(final int gameId,
                                          final int cashToBet,
                                          final int coefficient,
                                          @NonNull final Timestamp timestamp) throws InterruptedException {
        channel = openChannel();
        channel.writeAndFlush(MessageSender.getBetTransaction(gameId, cashToBet, coefficient, timestamp));
    }

    // TODO
    public void sendTokenTransactionMessage() {

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
