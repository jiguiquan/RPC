package com.jiguiquan.www;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这算是一个静态代理：
 * 代理的作用是，在客户端调用某个服务的时候，它不需要去关心底层的封装和协议，只需要关心它调用的逻辑就OK了
 * @author jigq
 * @create 2019-12-16 20:51
 */
public class RpcServerProxy {
    //创建一个无限核心线程数的线程池，只做演示，实际生产不这么用
    ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 这是一个发布服务的方法，将某个服务发布到某个端口上，想想SpringBoot
     * @param service 具体的服务
     * @param port 服务发布到的端口号
     */
    public void publisher(Object service, int port){

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);  //打开ServerSocket的一个服务监听
            while (true){  //使用 while (true) 进行持续监听
                Socket socket = serverSocket.accept();  //接收一个请求
                //**这里需要降到一个BIO模型，其实就是阻塞式IO————通过多线程去实现一个伪异步**
                executorService.execute(new ProcessorHandler(socket, service));  //执行实现了Rnnuable接口的线程
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
