package com.cn.jmw.demodesignmode.proxy.staticproxy;


import com.cn.jmw.demodesignmode.proxy.Person;

/**
 * Created by Tom on 2019/3/10.
 */
public class Father implements Person {
    private Son person;

    public Father(Son person){
        this.person = person;
    }

    public void findLove(){
        System.out.println("父亲物色对象");
        this.person.findLove();
        System.out.println("双方父母同意");
    }

    public void findJob(){

    }


}
