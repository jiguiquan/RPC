package com.jiguiquan.www;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 专门用来处理网络通信的transpost类
 * @author jiguiquan
 * @create 2019-12-17 21:25
 */
public class RPCNetTransport {
    String host;
    int port;

    public RPCNetTransport() {
    }

    public RPCNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket(){
        System.out.println("创建一个新的socket连接");

        Socket socket;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException("建立连接失败");
        }
        return socket;
    }

    public Object sendRequest(RPCRequest request){
        Socket socket = null;
        try {
            socket = newSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            //再构建一个输入流，用来读取返回结果用的
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
            return result;
        } catch (Exception e){
            throw new RuntimeException("发送数据异常："+e);
        } finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
