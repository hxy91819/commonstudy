package com.xiangyu.classloader;

/**
 * @author chenjing
 * @date 2020-07-25 18:02
 */
public class ClassLoaderDemo {
    public static void main(String[] args) {
        System.out.println("ClassLodarDemo's ClassLoader is " +
                ClassLoaderDemo.class.getClassLoader());
        System.out.println("The Parent of ClassLodarDemo's ClassLoader is " + ClassLoaderDemo.class.getClassLoader().getParent());
        System.out.println("The GrandParent of ClassLodarDemo's ClassLoader is " +
                ClassLoaderDemo.class.getClassLoader().getParent().getParent());
    }
}
