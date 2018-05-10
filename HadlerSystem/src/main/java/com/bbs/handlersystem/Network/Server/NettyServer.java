package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Config.Config;
import com.bbs.handlersystem.Database.MainStore;
import com.bbs.handlersystem.Database.Store;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.Executors;

public final class NettyServer implements Runnable {

    private Store store;
    private static final int PORT = Config.PORT;

    {
        store = new MainStore();
    }

    public synchronized void startServer() {
        Executors.newFixedThreadPool(1).execute(this);
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(store))
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_BACKLOG, Config.BACKLOG)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
