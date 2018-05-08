package com.bbs.handlersystem.Network.client;

import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Utils.JsonMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.Scanner;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want sent Person to server ?");
            if (scanner.next().equals("yes")) {
                System.out.println("write name: ");
                String name = scanner.next();
                System.out.println("write mobile number: ");
                String mobile = scanner.next();
                Gson builder = new GsonBuilder().setPrettyPrinting().create();
                User user = new User(name, mobile);
                JsonMessage jm = new JsonMessage(user, MessageType.MSG_DEFAULT);
                String message = builder.toJson(jm);
                ctx.writeAndFlush(message);
            } else {
                ctx.close();
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) {
        System.out.println("read");
        String receivedMessage = (String) object;
        System.out.println("client received: " + receivedMessage);
        ReferenceCountUtil.release(object);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("read complete");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
