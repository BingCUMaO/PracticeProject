package Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 通过传输层协议TCP/UDP协议，利用应用层协议HTTP
 * 编写一个Web Core.Server
 *
 * @author BinGCU
 */
public class Server {
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";
    private ServerSocket servSocket;
    private Socket client;
    private Dispatcher dispatcher;
    private boolean failToReceiveRequest = false;    //用来发送状态码505
    private boolean isRunning;


    //启动服务
    public void start() {
        try {
            servSocket = new ServerSocket(8888);
            isRunning = true;
            while (isRunning){
                acceptRequest();

                new Thread(this.dispatcher = new Dispatcher(client)).start();

                if(failToReceiveRequest){
                    this.dispatcher.setFailToReceiveRequest(true);
                }
            }

            System.out.println("Start Core.Server Successfully!");
        } catch (IOException e) {

            System.out.println("Fail To Start Core.Server...");
            stop();
        }

    }

    //停止服务
    public void stop() {
        if (client != null) {
            try {
                isRunning = false;

                servSocket.close();
                System.out.println("Stop Core.Server Successfully!");
            } catch (IOException e) {
                System.out.println("Fail To Stop Core.Server...");
                e.printStackTrace();
            }
        }


    }

    //接收请求
    public void acceptRequest() {
        BufferedReader br = null;

        try {
            client = servSocket.accept();   //阻塞式接收请求
            System.out.println("Receive One Core.Request Successfully！");
            System.out.println("------------------------------");

        } catch (IOException e) {

            System.out.println("Fail To Receive One Core.Request...");
            System.out.println("Please Check The Core.Request In Client");
            //状态码设置为505
            failToReceiveRequest = true;
        }


    }


}
