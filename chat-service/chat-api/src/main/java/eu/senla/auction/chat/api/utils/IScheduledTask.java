package eu.senla.auction.chat.api.utils;

import eu.senla.auction.chat.entity.entities.Message;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.concurrent.Callable;

public interface IScheduledTask {

    //    void checkMessages();
    Callable<String> sendNotification(String email, Message message, String chatId);

}
