package com.aikafka.dev.java8.future;

import java.util.concurrent.*;

/**
 * All rights Reserved, Designed By MiGu Copyright(C) 2017 Company MiGu Co., Ltd.
 * <p>
 * 〈一句话类描述〉 项目名称:咪咕合管 包名称:   com.aikafka.dev.java8.future 类名称:   FutureDemo 类描述: 创建人:   zhuxiaolong@aspirecn.com 创建时间:
 * 2017-11-13 10:09 版本：   V1.0.0
 *
 * @Author: zhuxiaolong@aspirecn.com
 */
public class FutureDemo {
    public static int print(int i, String s) {
        while (i != 0) {
            System.out.println(s + "\t" + i--);
        }
        return s.length();
    }

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        Future<Integer> future=executorService.submit(() -> print(1000000,"hello world"));

        System.out.println("xxxxxxxxxxxxxxxxxxxxxx");

        try {
            int result=future.get(1,TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
