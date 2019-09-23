package com.aspire.commons.designMode.singleton;

/**
 * 懒汉式，线程不安全
 * 这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。
 * 这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。
 * @author W
 * @createTime 2019/09/09 14:28
 * @see com.aspire.commons.designMode.singleton
 */
public class SingleObject1 {

    private static SingleObject1 instance;
    private SingleObject1 (){}

    public static SingleObject1 getInstance() {
        if (instance == null) {
            instance = new SingleObject1();
        }
        return instance;
    }

}
