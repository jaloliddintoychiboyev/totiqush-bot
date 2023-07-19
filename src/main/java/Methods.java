import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Methods extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long clientchat_id = update.getMessage().getChatId();
        String clientmessage = update.getMessage().getText();
        DbService dbService = new DbService();
        String regions[] = {"Tashkent", "Syrdarya", "Bukhara", "Andijan", "Namangan", "Jizzakh", "Fergana", "Navoiy"};
            if (clientmessage.equals("/start")) {
                String text = "Assalomu Alaykum " + message.getFrom().getFirstName() + "\n" +
                        "Telegram Botimizga Ulanganingizdan\n Juda Ham Mamnunmiz!\n" +
                        "Bot Siz Uchun Foydali Bo'ladi Degan Umiddamiz!❤️";
                try {
                    dbService.addUser(message.getFrom().getFirstName(), message.getFrom().getUserName(), String.valueOf(clientchat_id));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(clientchat_id);
                sendMessage.setText(text);
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                replyKeyboardMarkup.setOneTimeKeyboard(true);
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                KeyboardRow keyboardButtons = new KeyboardRow();
                KeyboardButton button = new KeyboardButton();
                button.setText("Ob-Havo Malumotlari");
                keyboardButtons.add(button);
                keyboardRowList.add(keyboardButtons);
                KeyboardRow keyboardButtons1 = new KeyboardRow();
                KeyboardButton button1 = new KeyboardButton();
                button1.setText("Admin");
                keyboardButtons1.add(button1);
                keyboardRowList.add(keyboardButtons1);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (clientmessage.equals("Ob-Havo Malumotlari")) {
                String text = message.getFrom().getFirstName() + "\nHududni Tanlang!";
                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setChatId(clientchat_id);
                sendMessage1.setText(text);
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                sendMessage1.setReplyMarkup(replyKeyboardMarkup);
                replyKeyboardMarkup.setOneTimeKeyboard(true);
                replyKeyboardMarkup.setSelective(true);
                replyKeyboardMarkup.setResizeKeyboard(true);
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                for (int i = 0; i < regions.length; i++) {
                    KeyboardRow keyboardButtons = new KeyboardRow();
                    KeyboardButton button = new KeyboardButton();
                    button.setText(regions[i]);
                    keyboardButtons.add(button);
                    keyboardRowList.add(keyboardButtons);
                }
                KeyboardRow keyboardButtons1 = new KeyboardRow();
                KeyboardButton button1 = new KeyboardButton();
                button1.setText("Admin");
                keyboardButtons1.add(button1);
                keyboardRowList.add(keyboardButtons1);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                try {
                    execute(sendMessage1);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (clientmessage.equals("Admin")){
                String text = "Admin: Jaloliddin To'ychiboyev\n\n Telegram :https://t.me/JaloliddinToychiboyev\n\n" +
                        "Instagram : https://instagram.com/jaloliddin.toychiboyev?igshid=MzNlNGNkZWQ4Mg==\n\n" +
                        "LinkedIn : https://www.linkedin.com/in/jaloliddin-to-ychiboyev-a68bb4280?lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base_contact_details%3BcoMzr5LHRFe1bh1uWdv%2FNw%3D%3D\n\n" +
                        "Gmail : jaloliddintoychiboyev5@gmail.com\n\n" +
                        "GitHub : https://github.com/jaloliddintoychiboyev\n\n" +
                        "Phone : +99894 852 1809";
                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setChatId(clientchat_id);
                sendMessage1.setText(text);
                try {
                    execute(sendMessage1);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            for (int i = 0; i < regions.length; i++) {
                if (clientmessage.equals(regions[i])) {
                    try {
                        Gson gson = new Gson();
                        SendMessage sendMessage = new SendMessage();
                        URL url = new URL("https://goweather.herokuapp.com/weather/" + regions[i]);
                        URLConnection connection = url.openConnection();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        Temperature temperature;
                        temperature = gson.fromJson(bufferedReader, new TypeToken<Temperature>() {
                        }.getType());
                        gson.fromJson(bufferedReader, new TypeToken<Temperature>() {
                        }.getType());
                        String text1 = "";
                        String description = temperature.getDescription();
                        String temperature1 = temperature.getTemperature();
                        String wind = temperature.getWind() + " \uD83C\uDF2C";
                        text1 = "Current Weather:\nDescription: " + description + "\n" + "Temperature: " + temperature1 + "\nWind: " + wind + "\nForecast:\n";
                        for (ForecastDay day : temperature.getForecast()) {
                            text1 += "Day " + day.getDay() + ": ";
                            text1 += "Temperature: " + day.getTemperature() + " \uD83C\uDF0E, ";
                            text1 += "Wind: " + day.getWind() + " \uD83C\uDF2C, ";
                            text1 += "\n";
                        }
                        sendMessage.setChatId(clientchat_id);
                        sendMessage.setText(text1);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


    }


    @Override
    public String getBotUsername() {
        return "https://t.me/codeuz_tg_bot";
    }
    @Override
    public String getBotToken() {
        return "6398898730:AAH1Oy0iNsiwNooQPa5RMFaS108WKpOhFIY";
    }
}
