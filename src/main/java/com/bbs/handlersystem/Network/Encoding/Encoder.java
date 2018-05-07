package com.bbs.handlersystem.Network.Encoding;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String jsonString, ByteBuf out) {
        out.writeBytes(jsonString.getBytes());
    }
}
