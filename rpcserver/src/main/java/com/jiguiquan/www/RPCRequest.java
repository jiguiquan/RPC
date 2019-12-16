package com.jiguiquan.www;

import lombok.Data;

import java.io.Serializable;

/**
 * 用来封装请求信息数据
 * @author jigq
 * @create 2019-12-16 21:18
 */
@Data
public class RPCRequest implements Serializable {
    private static final long serialVersionUID = 8555289438487145186L;
    private String className;
    private String methodName;
    private Object[] parameters;
}
