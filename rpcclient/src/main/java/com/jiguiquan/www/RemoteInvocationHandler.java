package com.jiguiquan.www;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 专门处理客户端请求的处理类
 * @author jiguiquan
 * @create 2019-12-17 21:19
 */
public class RemoteInvocationHandler implements InvocationHandler {
    String host;
    int port;

    public RemoteInvocationHandler() {
    }

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //组装一个RPCRequest对象
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        RPCNetTransport rpcNetTransport = new RPCNetTransport(host, port);
        return rpcNetTransport.sendRequest(rpcRequest);
    }
}
