package com.bbs.handlersystem.Network.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) {
        String receivedMessage = (String) object;
        System.out.println("client received: " + receivedMessage);
        ReferenceCountUtil.release(object);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    public void writeMessage(String messageToSend) {
        if (ctx != null) {
            ChannelFuture cf = ctx.writeAndFlush(Unpooled.copiedBuffer(messageToSend, CharsetUtil.UTF_8));
            if (!cf.isSuccess()) {
                System.out.println("Send failed: " + cf.cause());
            }
        } else {
            //ctx not initialized yet. you were too fast. do something here
            System.out.println("Oooops, something is wrong..");
        }
    }

}
