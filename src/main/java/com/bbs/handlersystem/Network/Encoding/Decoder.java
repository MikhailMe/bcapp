package com.bbs.handlersystem.Network.Encoding;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) {
        byte[] array = new byte[in.writerIndex() - in.readerIndex()];
        in.readBytes(array);
        String json = new String(array);
        out.add(json);
    }
}
