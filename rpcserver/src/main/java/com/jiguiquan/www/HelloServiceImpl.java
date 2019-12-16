package com.jiguiquan.www;

/**
 * 创建一个简单的业务处理类
 * @author jigq
 * @create 2019-12-16 20:44
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String content) {
        return "Hello world：" + content;
    }
    @Override
    public String saveUser(User user) {
        System.out.println("user->" + user);
        return "success";
    }
}
