package com.bottle.messenger.hibernate.factory;

import com.bottle.messenger.hibernate.model.Conversation;
import com.bottle.messenger.hibernate.model.Message;

import java.sql.SQLException;
import java.util.List;

public class MessengerDaoImpl implements MessengerDao {
    @Override
    public List<Conversation> getConversationsByUserId(int userId) throws SQLException {
        return null;
    }

    @Override
    public Message getLastMessageByConversationId(int conversationId) throws SQLException {
        return null;
    }

    @Override
    public List<Message> getMessageListByConversationId(int conversationId) throws SQLException {
        return null;
    }

    @Override
    public void addMessage(Message message) throws SQLException {

    }

    @Override
    public void addConversation(Conversation conversation) throws SQLException {

    }
}