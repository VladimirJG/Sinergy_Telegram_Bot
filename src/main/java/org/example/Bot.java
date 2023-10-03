package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.net.URL;

public class Bot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "Synergy_My_Bot";
    }

    @Override
    public String getBotToken() {
        return "6594201745:AAFaxqXzfyIusGK90bkOzbCTcLEOE_UPhE0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        PhotoSize photoSize = message.getPhoto().get(0);
        final String fileIdd = photoSize.getFileId();

        try {
            final org.telegram.telegrambots.meta.api.objects.File file = sendApiMethod(new GetFile(fileIdd));
            final String imageUrl = "https://api.telegram.org/file/bot" + getBotToken() + "/" + file.getFilePath();
            saveImage(imageUrl, "receive_image.jpg");
        } catch (TelegramApiException | IOException e) {
            throw new RuntimeException(e);
        }

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(message.getChatId().toString());
        InputFile newFile = new InputFile();
        newFile.setMedia(new File("image.jpg"));
        sendPhoto.setPhoto(newFile);
        sendPhoto.setCaption("Панда копанда");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Ответное сообщение: " + message.getText());

        try {
            execute(sendMessage);
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String url, String fileName) throws IOException {
        URL urlModel = new URL(url);
        InputStream inputStream = urlModel.openStream();
        OutputStream outputStream = new FileOutputStream(fileName);

        byte[] b = new byte[2048];
        int length;


        while ((length = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}
