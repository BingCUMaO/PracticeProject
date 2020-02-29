package MyWebServer;

import Servlet.HomepageServlet;
import Servlet.LoginServlet;
import Servlet.RegisterServlet;
import Servlet.Servlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过传输层协议TCP/UDP协议，利用应用层协议HTTP
 * 编写一个Web Server
 *
 * @author BinGCU
 */
public class Server {
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";
    private ServerSocket servSocket;
    private Socket client;

    public static void main(String[] args) {
        Server serv1 = new Server();
        serv1.start();
        serv1.stop();
    }

    //启动服务
    public void start() {
        try {
            servSocket = new ServerSocket(8888);
            System.out.println("Start Server Successfully!");
        } catch (IOException e) {
            System.out.println("Fail To Stop Server...");
            e.printStackTrace();
        }

        receiveRequest();
    }

    //停止服务
    public void stop() {
        if (client != null) {
            try {
                client.close();
                servSocket.close();
                System.out.println("Stop Server Successfully!");
            } catch (IOException e) {
                System.out.println("Fail To Stop Server...");
                e.printStackTrace();
            }
        }


    }

    //接收请求
    public void receiveRequest() {
        BufferedReader br = null;

        if (servSocket != null) {
            try {
                client = servSocket.accept();
                System.out.println("Receive One Request Successfully！");
                System.out.println("------------------------------");
            } catch (IOException e) {
                System.out.println("Fail To Receive One Request...");
                System.out.println("Please Check The Request In Client");
                e.printStackTrace();
            }
        }

        respondRequest();
    }

    //响应请求
    public void respondRequest() {

        Request request = new Request(client);
        Responder responder = new Responder(client);

        Servlet servlet = null;
        if(request.getUrl().equals("login")){
            servlet = new LoginServlet();
        } else if (request.getUrl().equals("register")) {
            servlet = new RegisterServlet();
        }else{
            //跳转到主页或其他页面，目前还未学习相关知识，之后再补充完善功能
            servlet = new HomepageServlet();
        }


        servlet.service(request,responder);

        servlet.responseRequest(200);
    }
}
