package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Network.Message.MessageType;
import com.google.gson.*;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) {
        String jsonString = (String) object;
        JsonElement jsonRoot = new JsonParser().parse(jsonString);
        JsonObject jsonTree = jsonRoot.getAsJsonObject();
        JsonElement messageType = jsonTree.get("type");
        String stringType = messageType.toString();
        stringType = stringType.substring(1, stringType.length() - 1);
        MessageType type = MessageType.valueOf(stringType);
        switch (type) {
            case MSG_GET_CLIENT_INFO_SYN:
                break;
            case MSG_GET_CLIENT_INFO_ACK:
                break;
            case MSG_GET_LIST_GAMES_SYN:
                break;
            case MSG_GET_LIST_GAMES_SYN_ACK:
                break;
            case MSG_GET_LIST_GAMES_ACK:
                break;
            case MSG_TAKE_TRANSACTION_SYN:
                break;
            case MSG_TAKE_TRANSACTION_ACK:
                break;
            case MSG_SET_ORACLE_SYN:
                break;
            case MSG_SET_ORACLE_ACK:
                break;
            case MSG_DEFAULT:
                System.out.println(jsonString);
                ctx.writeAndFlush(Unpooled.copiedBuffer("server", CharsetUtil.UTF_8));
                break;
            default:
                break;
        }
        //ctx.channel().writeAndFlush(new JsonMessage(object, MessageType.MSG_DEFAULT).toString());

        //JsonElement userElement = jsonTree.get("data");
        //User u = new Gson().fromJson(userElement, User.class);

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
}