package com.jiguiquan.www;

/**
 * 项目启动类
 */
public class App 
{
    public static void main( String[] args )
    {
        HelloService helloService = new HelloServiceImpl();
        RpcServerProxy rpcServerProxy = new RpcServerProxy();
        rpcServerProxy.publisher(helloService, 9090);
    }
}
