package com.aspire.commons.designMode.factory;

/**
 * @author W
 * @createTime 2019/09/09 11:37
 * @see com.aspire.commons.designMode.factory
 */
public class Rectangle implements Shape{
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
