package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Client.Wallet;
import com.bbs.handlersystem.Database.StoreImpl.MainStore;
import com.bbs.handlersystem.Network.Helpers.ClientInfoWrapper;
import com.bbs.handlersystem.Network.Message.JsonMessage;
import com.bbs.handlersystem.Network.Message.MessageMaker;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.google.gson.*;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.sql.SQLException;

@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final String DATA = "data";
    private static final String TYPE = "type";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object object) throws SQLException {
        String jsonString = (String) object;
        JsonElement jsonRoot = new JsonParser().parse(jsonString);
        JsonObject jsonTree = jsonRoot.getAsJsonObject();
        JsonElement messageType = jsonTree.get(TYPE);
        String stringType = messageType.toString();
        stringType = stringType.substring(1, stringType.length() - 1);
        MessageType type = MessageType.valueOf(stringType);
        String messageToSend;
        switch (type) {
            case MSG_ADD_USER:
                // get user object from json
                JsonElement userElement = jsonTree.get(DATA);
                // creating objects
                User user = new Gson().fromJson(userElement, User.class);
                Wallet wallet = new Wallet(user);
                Account account = new Account(wallet);
                // putting objects to stores
                MainStore.userStore.add(user);
                MainStore.walletStore.add(wallet);
                MainStore.accountStore.add(account);
                messageToSend = "just adding user to database";
                break;
            case MSG_REQUEST_CLIENT_INFO:
                // we has name
                String name = "alewa";
                long userId = MainStore.userStore.getId(name);
                long balance = MainStore.walletStore.getBalance(userId);
                ClientInfoWrapper clientInfoWrapper = new ClientInfoWrapper(name, balance);
                JsonMessage jm = new JsonMessage<>(clientInfoWrapper, MessageType.MSG_RESPONSE_CLIENT_INFO);
                messageToSend = jm.toJson();
                break;
            case MSG_GET_LIST_OF_GAMES:
                // TODO
                JsonElement requestListOfGames = jsonTree.get(DATA);
                messageToSend = MessageMaker.getListOfGamesMessage();
                break;
            default:
                //messageToSend = MessageMaker.getAddUserMessage(user);
                messageToSend = "default";
                break;
        }

        ctx.writeAndFlush(Unpooled.copiedBuffer(messageToSend, CharsetUtil.UTF_8));
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
