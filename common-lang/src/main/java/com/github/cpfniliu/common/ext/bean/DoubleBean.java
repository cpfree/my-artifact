package com.github.cpfniliu.common.ext.bean;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/9 18:29
 */
public class DoubleBean<O1, O2> {

    public static <O, T> DoubleBean<O, T> of(O o, T t) {
        return new DoubleBean<>(o, t);
    }

    private O1 o1;
    private O2 two;

    private DoubleBean(O1 o, O2 t) {
        this.o1 = o;
        this.two = t;
    }

    public O1 getO1() {
        return o1;
    }

    public void setO1(O1 o1) {
        this.o1 = o1;
    }

    public O2 getTwo() {
        return two;
    }

    public void setTwo(O2 two) {
        this.two = two;
    }
}
