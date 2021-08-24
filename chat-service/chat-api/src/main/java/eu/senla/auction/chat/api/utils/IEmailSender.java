package eu.senla.auction.chat.api.utils;

import eu.senla.auction.chat.entity.entities.Chat;

public interface IEmailSender {

    void sendEmailToBuyer(Chat chat);

    void sendEmailToDealer(Chat chat);

    void sendNotificationEmail(String email, String chatId) throws Exception;

}
