package com.skillbox.redisdemo;

import java.util.*;

import static java.lang.System.out;
import static java.lang.Thread.*;

public class RedisTest {

    //Время между отображением пользователей
    private static final int SLEEP = 1000;

    // Вспомогательная переменная, на случай оплаты
    private static boolean triggerOne;

    //Переменная для хранения id пользователя, который произвёл оплату
    private static String userPay;


    public static void main(String[] args) throws InterruptedException {

        RedisStorage redis = new RedisStorage();
        redis.init();
        for(int user_id = 0; user_id < 20; user_id++) {
            redis.logPage(user_id);
            sleep(SLEEP);
        }

        while (true) {
            LinkedHashMap<String, Date> map = redis.convertToMap();
            for (Map.Entry<String, Date> e : map.entrySet()) {


                // Имитация оплаты пользователем 10, по время очереди пользователя 5
                if (e.getKey().equals("5")){
                    pay("10");
                }
                if (Objects.equals(userPay, e.getKey()) & triggerOne){
                    triggerOne = false;
                    continue;
                }
                print(e.getKey());
                sleep(SLEEP);
            }
        }
    }

    // Метод, срабатывает только после оплаты пользователем услуги, а так же выводит оплатившего пользователя
    private static void pay(String userId) throws InterruptedException {
        userPay = userId;
        out.println("> Пользователь " + userPay + " оплатил платную услугу");
        print(userPay);
        sleep(SLEEP);
        triggerOne = true;
    }

    static void print(String userId){
        out.println("— На главной странице показываем пользователя " + userId);
    }
}
