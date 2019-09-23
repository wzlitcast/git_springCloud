package com.aspire.commons.designMode.singleton;

/**
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 * 这种方式采用双锁机制，安全且在多线程情况下能保持高性能。getInstance() 的性能对应用程序很关键。
 * @author W
 * @createTime 2019/09/09 14:28
 * @see com.aspire.commons.designMode.singleton
 */
public class SingleObject3 {

    private volatile static SingleObject3 singleton;
    private SingleObject3 (){}
    public static SingleObject3 getSingleton() {
        if (singleton == null) {
            synchronized (SingleObject3.class) {
                if (singleton == null) {
                    singleton = new SingleObject3();
                }
            }
        }
        return singleton;
    }
}
