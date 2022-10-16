package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(int shift) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        return date.plusDays(shift).format(dateFormat);
    }

    public static String generateCity() {
        String cities[] = {"Москва", "Оренбург", "Челябинск", "Иваново", "Магас", "Уфа", "Махачкала", "Владикавказ"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateCityErr() {
        String cities[] = {"Норильск", "Магнитогорск"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName() {
        return faker.name().lastName().replace("ё", "е") + " " + faker.name().firstName().replace("ё", "е");
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }
    public static String generateErrorPhone() {
        return faker.numerify("+7######");
    }

    public static class Registration {
        private Registration() {

        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(), generatePhone());
        }

        public static UserInfo generateUser2(String locale) {
            return new UserInfo(generateCityErr(), generateName(), generatePhone());
        }
        public static UserInfo generateUser3(String locale) {
            return new UserInfo(generateCity(), generateName(), generateErrorPhone());
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}
