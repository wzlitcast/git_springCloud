package com.aspire.commons.designMode.factory;

/**
 * @author W
 * @createTime 2019/09/09 11:46
 * @see com.aspire.commons.designMode.factory
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
