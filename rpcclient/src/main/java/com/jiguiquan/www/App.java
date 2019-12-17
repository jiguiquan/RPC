package com.jiguiquan.www;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RpcClientProxy proxy = new RpcClientProxy();
        HelloService helloService = proxy.clientProxy(HelloService.class, "localhost", 9090);
        User user = new User();
        user.setName("吉桂权");
        System.out.println(helloService.saveUser(user));
    }
}
