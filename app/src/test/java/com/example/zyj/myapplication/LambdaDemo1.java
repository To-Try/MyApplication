package com.example.zyj.myapplication;

import com.encdata.zyj.myapplication.lambda.Persion;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Lambda 表达式有何用处？如何使用？
 * 这里的Persion只能引用工程里面的，因为@Data注解只在工程里面才有效果
 * https://www.zhihu.com/question/20125256/answer/324121308
 * Created by zhaoyuejun on 2018/4/12.
 */

public class LambdaDemo1 {

    private List<Persion> guiltyPersions = new ArrayList<>();

    public void init() {
        Persion persion1 = new Persion("Yixing", "Zhao", 23);
        Persion persion2 = new Persion("Yanggui", "Li", 23);
        Persion persion3 = new Persion("Chao", "Ma", 23);
        guiltyPersions.add(persion1);
        guiltyPersions.add(persion2);
        guiltyPersions.add(persion3);
    }

    /**
     * 打印出guiltyPersons List里面所有LastName以"Z"开头的人的FirstName。
     * 原生态Lambda写法：定义两个函数式接口，定义一个静态函数，调用静态函数并给参数赋值Lambda表达式
     */
    @Test
    public void test1() {
        init();
        checkAndExecute(guiltyPersions, persion -> persion.getLastName().startsWith("Z"), persion -> Klog.pl(persion.getFirstName()));
//        输出：
//        D/LOG------: Yixing
    }

    @FunctionalInterface
    interface NameChecker {
        boolean check(Persion persion);
    }

    @FunctionalInterface
    interface Executor {
        void execute(Persion persion);
    }

    public static void checkAndExecute(List<Persion> persions, NameChecker nameChecker, Executor executor) {
        for (Persion persion : persions) {
            if (nameChecker.check(persion)) {
                executor.execute(persion);
            }
        }
    }
    /******************************************************************************************************************/
    /**
     * 在Java 8中有一个函数式接口的包，里面定义了大量可能用到的函数式接口
     * 所以，我们在这里压根都不需要定义NameChecker和Executor这两个函数式接口，
     * 直接用Java 8函数式接口包里的Predicate<T>和Consumer<T>就可以了——因为他们这一对的接口定义和NameChecker/Executor其实是一样的。
     */
//    第一步简化 - 利用函数式接口包：
    public void checkAndExecute1(List<Persion> persions, Predicate<Persion> predicate, Consumer<Persion> consumer) {
        //静态函数里面的for each循环其实是非常碍眼的。
        //这里可以利用Iterable自带的forEach()来替代。forEach()本身可以接受一个Consumer<T> 参数。
        for (Persion persion : persions) {
            if (predicate.test(persion))
                consumer.accept(persion);
        }
    }

    //    第二步简化 -用Iterable.forEach() 取代foreach loop：
    public void checkAndExecute2(List<Persion> persions, Predicate<Persion> predicate, Consumer<Persion> consumer) {
        persions.forEach(persion -> {
            if (predicate.test(persion)) consumer.accept(persion);
        });
    }

    @Test
    public void test2() {
        init();
        checkAndExecute1(guiltyPersions, persion -> persion.getLastName().startsWith("Z"), persion -> Klog.pl(persion.getFirstName()));
        checkAndExecute2(guiltyPersions, persion -> persion.getLastName().startsWith("Z"), persion -> Klog.pl(persion.getFirstName()));
//        输出：
//        D/LOG------: Yixing
//        D/LOG------: Yixing
    }
}

