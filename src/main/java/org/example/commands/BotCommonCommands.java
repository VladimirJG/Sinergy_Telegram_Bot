package org.example.commands;

public class BotCommonCommands {
    @AppBotCommand(name = "/hello", description = "when request hello", showInHelp = true)
    String hello(){
        return "Привет, дружище! Давно не виделись :)";
    }

    @AppBotCommand(name = "/bye", description = "when request bye", showInHelp = true)
    String bye(){
        return "Ты уже уходишь? Как жаль... Приходи снова!";
    }

    @AppBotCommand(name = "/help", description = "when request help", showInKeyboard = true)
    String help(){
        return "Вы воспользовались командой /help. В дальнейшем в данном разделе будут все доступные команды бота.";
    }
}
