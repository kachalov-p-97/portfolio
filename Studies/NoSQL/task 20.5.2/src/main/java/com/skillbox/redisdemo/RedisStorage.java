package com.skillbox.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.client.protocol.ScoredEntry;
import org.redisson.config.Config;

import java.util.*;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
    protected RedissonClient redisson;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    // Наименование ключа в БД Redis
    protected final static String KEY = "ONLINE_USERS";


    // Метод устанавливающий конфигурации и подключение к Redis
    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        // Объект для работы с ключами
        onlineUsers = redisson.getScoredSortedSet(KEY);
    }

    // Метод возвращающий дату и время на момент его вызова
    private double getTs() {
        return new Date().getTime() / 1000;
    }

    // Метод возвращает LinkedHashMap пользователей отсортированных по времени регистрации
    public LinkedHashMap<String, Date> convertToMap () {
        RScoredSortedSet<String> sortedSet = redisson.getScoredSortedSet(KEY);
        Map<String, Double> stringDateMap = new HashMap<>();

        for (ScoredEntry<String> tuple :sortedSet.entryRangeReversed(0,-1)){
            stringDateMap.put(tuple.getValue(),tuple.getScore());
        }
        LinkedList<Map.Entry<String , Double>> list = new LinkedList<>(stringDateMap.entrySet());
        list.sort(Map.Entry.comparingByValue());

        LinkedHashMap<String,Date> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> m : list){
            Date date = new Date(Math.round(m.getValue() * 1000));
            sortedMap.put(m.getKey(),date);
        }
        return sortedMap;
    }



    // Фиксирует регистрацию пользователя
    void logPage(int userId)
    {
        //ZADD ONLINE_USERS
        onlineUsers.add(getTs(), String.valueOf(userId));

    }

    // Метод завершающий сессию с RedisClient
    void shutdown() {
        redisson.shutdown();
    }

    // Метод возвращающий количество зарегистрированных пользователей
    int calculateUsersNumber()
    {
        // ZCOUNT ONLINE_USERS
        return onlineUsers.count(Double.NEGATIVE_INFINITY, true, Double.POSITIVE_INFINITY, true);
    }
}
