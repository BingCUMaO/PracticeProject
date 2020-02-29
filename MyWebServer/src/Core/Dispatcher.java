package Core;

import ConfigXML.WebApp;
import Servlet.NotFoundServlet;
import Servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 分发器
 * 实现对服务器的多线程管理
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private boolean failToReceiveRequest = false;    //用来发送状态码505


    public Dispatcher(Socket client) {
        this.client = client;
    }

    public void setFailToReceiveRequest(boolean failToReceiveRequest) {
        this.failToReceiveRequest = failToReceiveRequest;
    }

    @Override
    public void run() {
        this.respondRequest();
        this.release(client);
    }

    public void release(Socket client) {
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Fail To Stop Client!");
        }
    }



    //响应请求
    public void respondRequest() {

        Request request = new Request(client);
        Responder responder = new Responder(client);

        Servlet servlet = WebApp.getServletByUrl(request.getUrl());     //使用XML配置
//        不需要这个，因为通过XML配置文件来拓展Servlet类耦合度低
//        要是使用if的方法，则以后每添加一个Servlet类就要来修改代码
//        if(request.getUrl().equals("login")){
//            servlet = new LoginServlet();
//        } else if (request.getUrl().equals("register")) {
//            servlet = new RegisterServlet();
//        }else{
//            //跳转到主页或其他页面，目前还未学习相关知识，之后再补充完善功能
//            servlet = new HomepageServlet();
//        }


        //按情况传入状态码，剩下的其他操作交由responseRequest()
        if (null == servlet) {
            servlet = new NotFoundServlet();

            servlet.service(request, responder);

            //读取error.html文件发送至客户端
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("content/error.html");
            try {
                responder.print(new String(is.readAllBytes()));

                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Fail To Read File!");
            }


            servlet.responseRequest(404);

        } else {
            servlet.service(request, responder);

            if (failToReceiveRequest){
                servlet.responseRequest(505);
            }
            else{
                servlet.responseRequest(200);
            }

        }


    }
}
