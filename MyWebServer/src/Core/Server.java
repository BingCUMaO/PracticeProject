package Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * ͨ�������Э��TCP/UDPЭ�飬����Ӧ�ò�Э��HTTP
 * ��дһ��Web Core.Server
 *
 * @author BinGCU
 */
public class Server {
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";
    private ServerSocket servSocket;
    private Socket client;
    private Dispatcher dispatcher;
    private boolean failToReceiveRequest = false;    //��������״̬��505
    private boolean isRunning;


    //��������
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

    //ֹͣ����
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

    //��������
    public void acceptRequest() {
        BufferedReader br = null;

        try {
            client = servSocket.accept();   //����ʽ��������
            System.out.println("Receive One Core.Request Successfully��");
            System.out.println("------------------------------");

        } catch (IOException e) {

            System.out.println("Fail To Receive One Core.Request...");
            System.out.println("Please Check The Core.Request In Client");
            //״̬������Ϊ505
            failToReceiveRequest = true;
        }


    }


}
