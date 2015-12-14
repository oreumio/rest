package com.oreumio.rest;

/**
 * Created by jchoi on 12/14/15.
 */
public class MyVo {

    private String first;

    private String last;

    public MyVo(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
