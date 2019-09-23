package com.aspire.commons.designMode.factory;

/**
 * @author W
 * @createTime 2019/09/09 11:45
 * @see com.aspire.commons.designMode.factory
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
