package com.jiguiquan.www;

import java.lang.reflect.Proxy;

/**
 * 这里我们将使用动态代理的方式来实现此客户端代理，需要用到泛型：
 * 动态代理的实现有很多方法，比如：
 *      --java原生的proxy
 *      --cglib
 *      --javasist等；
 * @author jiguiquan
 * @create 2019-12-17 21:13
 */
public class RpcClientProxy {
    /**
     * 动态代理的实现，需要用到泛型：
     * @param interfaceCls 相当于告诉需要代理哪个类，最后的结果将会返回远程类的代理，如代理的是HelloService类，那么就会返回HelloService供使用
     * @param host
     * @param port
     * @param <T>
     * @return
     */
    public <T> T clientProxy(Class<T> interfaceCls, String host, int port){
        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new RemoteInvocationHandler(host, port));
    }
}
