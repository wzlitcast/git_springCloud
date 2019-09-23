package com.aspire.commons.leetcode;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @description: leetcode 测试
 **/
public class LeetcodeTest {

    public static void main(String[] args) {
        //String[] tokens = {"1","2","+","3","/"};
        //System.out.println(evalRPN(tokens));

        Point[] points = {
                new Point(2,3),
                new Point(2,4),
                new Point(5,3),
                new Point(6,3)
        };
        System.out.println(maxPoints(points));
    }


    /**
     * 计算逆波兰式（后缀表达式）的值
     * 运算符仅包含"+","-","*"和"/"，被操作数可能是整数或其他表达式
     * 例如：
     *    ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9↵  ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
     */
    private static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0;i<tokens.length;i++){
            try{
                int num = Integer.parseInt(tokens[i]);
                stack.add(num);
            }catch (Exception e) {
                int b = stack.pop();
                int a = stack.pop();
                stack.add(get(a, b, tokens[i]));
            }
        }
        return stack.pop();
    }
    private static int get(int a, int b, String operator){
        switch (operator) {
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "/":
                return a/b;
            default:
                return 0;
        }
    }

    /**
     * 对于给定的n个位于同一二维平面上的点，求最多能有多少个点位于同一直线上
     */
    private static int maxPoints(Point[] points){
        int n = points.length;
        if(n < 2) return n;

        int ret = 0;
        for(int i = 0; i < n; i++) {
            // 分别统计与点i重合以及垂直的点的个数
            int dup = 1, vtl = 0;
            Map<Float, Integer> map = new HashMap<>();
            Point a = points[i];

            for(int j = 0; j < n; j++) {
                if(i == j) continue;
                Point b = points[j];
                if(a.x == b.x) {
                    if(a.y == b.y) dup++;
                    else vtl++;
                } else {
                    float k = (float)(a.y - b.y) / (a.x - b.x);
                    if(map.get(k) == null) map.put(k, 1);
                    else map.put(k, map.get(k) + 1);
                }
            }

            int max = vtl;
            for(float k: map.keySet()) {
                max = Math.max(max, map.get(k));
            }
            ret = Math.max(ret, max + dup);
        }
        return ret;
    }
}
