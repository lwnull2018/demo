package com.jack.chen.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 电商网站比价需求分析：
 * 1. 需求说明：
 *   a. 同一款产品，同时搜索出同款产品在各大电商平台的售价
 *   b. 同一款产品，同时搜索出本产品在同一个电商平台下，各个入驻卖家售价是多少
 * 2. 输出返回：
 *   a. 出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String>
 *   例如：《Mysql》 in jd price is 88.05  《Mysql》 in taobao price is 90.43
 * 3. 解决方案，对比同一个产品在各个平台上的价格，要求获得一个清单列表
 *   a. step by step，按部就班，查完淘宝查京东，查完京东查天猫....
 *   b. all in，万箭齐发，一口气多线程异步任务同时查询
 *
 */
public class CompletableFutureMallDemo {

    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("tmall")
    );

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();

        List<String> priceList = getPrice(list, "mysql");
        for (String ele : priceList) {
            System.out.println(ele);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("------costTime: " + (endTime - startTime) + " 毫秒");


        long startTime2 = System.currentTimeMillis();

        List<String> priceList2 = getPriceByCompletableFuture(list, "mysql");
        for (String ele : priceList2) {
            System.out.println(ele);
        }

        long endTime2 = System.currentTimeMillis();

        System.out.println("------costTime: " + (endTime2 - startTime2) + " 毫秒");


    }

    /**
     * all in
     * 把list里面的内容映射给CompletableFuture()
     * 《Mysql》 in taobao price is 90.43
     * @param list
     * @param productName
     */
    private static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall ->
                CompletableFuture.supplyAsync(() -> {
                    return String.format("《%s》in %s price is %.2f ", productName, netMall.getNetMallName(), netMall.calcPrice(productName));
                }))
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }

    /**
     * step by step
     * 《Mysql》 in taobao price is 90.43
     * @param list
     * @param productName
     */
    private static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream().map(netMall ->
                String.format("《%s》in %s price is %.2f ", productName, netMall.getNetMallName(), netMall.calcPrice(productName))
            ).collect(Collectors.toList());
    }

}

@NoArgsConstructor
@AllArgsConstructor
@Data
class NetMall {

    private String netMallName;

    public double calcPrice(String produceName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + produceName.charAt(0);
    }

}
