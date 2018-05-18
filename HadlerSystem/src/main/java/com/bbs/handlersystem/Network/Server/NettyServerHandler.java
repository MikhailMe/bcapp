package com.bbs.handlersystem.Network.Server;

import com.bbs.handlersystem.Client.Bet;
import com.bbs.handlersystem.Client.Account;
import com.bbs.handlersystem.Client.User;
import com.bbs.handlersystem.Client.Wallet;
import com.bbs.handlersystem.Data.Game;
import com.bbs.handlersystem.Database.StoreImpl.MainStore;
import com.bbs.handlersystem.Network.ContentMessage.ContentOfClientInfoMessage;
import com.bbs.handlersystem.Network.ContentMessage.ContentOfGameMessage;
import com.bbs.handlersystem.Network.ContentMessage.ContentOfSimpleMessage;
import com.bbs.handlersystem.Network.Message.JsonMessage;
import com.bbs.handlersystem.Network.Message.MessageType;
import com.bbs.handlersystem.Token.Token;
import com.bbs.handlersystem.Transaction.BetTransaction;
import com.bbs.handlersystem.Transaction.Transaction;
import com.bbs.handlersystem.Utils.Helper;
import com.bbs.handlersystem.Validator.Validator;
import com.google.gson.*;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.sql.SQLException;
import java.util.List;

@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final String DATA = "data";
    private static final String TEXT = "text";
    private static final String TYPE = "type";
    private static final String NICKNAME = "nickname";
    private static final String MOBILE_NUMBER = "mobileNumber";

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
                JsonElement userElement = jsonTree.get(DATA);
                JsonObject userObject = userElement.getAsJsonObject();
                String nickname = userObject.get(NICKNAME).toString();
                String mobileNumber = userObject.get(MOBILE_NUMBER).toString();

                User user = new User(nickname, mobileNumber);
                Wallet wallet = new Wallet(user);
                Account account = new Account(wallet);
                MainStore.userStore.add(user);
                MainStore.walletStore.add(wallet);
                MainStore.accountStore.add(account);
                messageToSend = "User " + user.toString() + " successfully added to database";
                break;
            case MSG_REQUEST_CLIENT_INFO:
                String name = jsonTree.get(DATA).getAsJsonObject().get(TEXT).toString();
                long userId = MainStore.userStore.getId(name);
                long balance = MainStore.walletStore.getBalance(userId);
                ContentOfClientInfoMessage clientInfo = new ContentOfClientInfoMessage(name, balance);
                JsonMessage clientInfoMessage = new JsonMessage<>(clientInfo, MessageType.MSG_RESPONSE_CLIENT_INFO);
                messageToSend = clientInfoMessage.toJson();
                break;
            case MSG_REQUEST_LIST_OF_GAMES:
                // TODO: write real parser for get list of games
                List<Game> games = Helper.createListOfGames(8);
                games.forEach(game -> {
                    try {
                        MainStore.gameStore.add(game);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                List<ContentOfGameMessage> listOfGames = Helper.getListGameMessages(games);
                JsonMessage listOfGamesMessage = new JsonMessage<>(listOfGames, MessageType.MSG_RESPONSE_LIST_OF_GAMES);
                messageToSend = listOfGamesMessage.toJson();
                break;
            case MSG_REQUEST_TRANSACTION:
                JsonElement betElement = jsonTree.get(DATA);
                Bet betInfo = new Gson().fromJson(betElement, Bet.class);

                Transaction betTransaction = new BetTransaction(
                        betInfo.getGameId(),
                        betInfo.getCashToBet(),
                        betInfo.getUser(),
                        betInfo.getTimestamp(),
                        betInfo.getCoefficient()
                );

                boolean isValid = Validator.isValidBetTransaction(betTransaction);
                String decision;
                if (isValid) {
                    MainStore.betStore.add(betInfo);
                    decision = "transaction accepted";
                } else {
                    decision = "transaction failure";
                }

                // ADD ORACLE
                ContentOfSimpleMessage response = new ContentOfSimpleMessage(decision);
                JsonMessage responseOnBetTransaction = new JsonMessage<>(response, MessageType.MSG_RESPONSE_TRANSACTION);
                messageToSend = responseOnBetTransaction.toJson();
                break;
            case MSG_REQUEST_ORACLE:
                User uzver = new User("boss", "777");
                JsonElement oracleElement = jsonTree.get(DATA).getAsJsonObject();
                String answer = new Gson().fromJson(oracleElement, ContentOfSimpleMessage.class).getText();
                boolean isOracleNow = answer.equals("y");
                if (isOracleNow) {
                    Token newToken = new Token();
                    long tokenId = MainStore.tokenStore.add(newToken);
                    uzver.setIsOracle(isOracleNow);
                    uzver.setTokenId(tokenId);
                    messageToSend = "now you are an oracle!";
                } else {
                    messageToSend = "client is not an oracle";
                }
                break;
            default:
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
