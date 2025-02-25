package part1.common.Message;

import lombok.AllArgsConstructor;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:29
 */

//enum（枚举）是一种特殊的类，用于定义 一组固定的常量。
@AllArgsConstructor
public enum MessageType {
    REQUEST(0),RESPONSE(1);
    private int code;
    public int getCode(){
        return code;
    }
}