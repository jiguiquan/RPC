package com.jiguiquan.www;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 这个就是用来处理Socket请求的，可以理解为服务器上真正处理业务的类（支持多线程）
 * @author jigq
 * @create 2019-12-16 21:04
 */
public class ProcessorHandler implements Runnable {

    Socket socket;
    Object service;

    public ProcessorHandler() {
    }

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {  //在这里处理真正的业务
        System.out.println("开始处理客户端请求");
        ObjectInputStream inputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());  //获取网络Socket输入流
            RPCRequest rpcRequest = (RPCRequest) inputStream.readObject();  //readObject()就是java原生反序列化的过程
            Object result = invoke(rpcRequest);
            //将这个结果result返回给客户端
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());  //建立一个socket通信管道
            objectOutputStream.writeObject(result);  //将结果写进去
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 更具体的业务处理方法，获取到类名-方法名-参数，使用反射来处理请求
     * @param request
     * @return
     */
    private Object invoke(RPCRequest request){
        Object[] args = request.getParameters();
        //因为在调用方法的时候，我们需要知道参数的类型，因为在调用方法的时候需要传递类型
        //使用Class[]数组来接收参数的类型
        Class<?>[] types = new Class[args.length];
        for(int i=0; i< args.length; i++){
            types[i] = args[i].getClass();
        }
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), types);
            //其实service我们也是可以从request.getClassName()后，通过反射得到处理类对应的对象
            Object result = method.invoke(service, args);  //invoke执行方法
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
