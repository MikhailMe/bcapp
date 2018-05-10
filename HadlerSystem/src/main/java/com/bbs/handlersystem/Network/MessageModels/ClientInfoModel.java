package com.bbs.handlersystem.Network.MessageModels;

import com.bbs.handlersystem.Network.Message.Message;
import com.bbs.handlersystem.Network.Message.MessageType;

public class ClientInfoModel extends Message {

    protected ClientInfoModel(MessageType type) {
        super(type);
    }
}
