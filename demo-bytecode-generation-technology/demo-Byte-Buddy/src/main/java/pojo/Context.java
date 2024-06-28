package pojo;

import lombok.Data;

// 定义处理上下文
@Data
public class Context<T, R> {

    private T input;
    private R output;

    public Context(T input){
        this.input = input;
    }

    public Context(){}

}