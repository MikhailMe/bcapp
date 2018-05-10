package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Utils.JsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Scanner;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channel active");
        this.ctx = ctx;

        Scanner scanner = new Scanner(System.in);
        System.out.println("write name: ");
        String name = scanner.next();
        System.out.println("write mobile number: ");
        String mobile = scanner.next();

        Gson builder = new GsonBuilder().setPrettyPrinting().create();

        User user = new User(name, mobile);

        JsonMessage jm = new JsonMessage(user, MessageType.MSG_ADD_USER);

        String message = builder.toJson(jm);

        ctx.writeAndFlush(message);

        System.out.println("end active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("channel inactive");
        channelActive(ctx);
        System.out.println("end inactive");
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
        System.out.println("start message");
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


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

    }
}
