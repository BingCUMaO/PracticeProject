package Core;

import ConfigXML.WebApp;
import Servlet.NotFoundServlet;
import Servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * �ַ���
 * ʵ�ֶԷ������Ķ��̹߳���
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private boolean failToReceiveRequest = false;    //��������״̬��505


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



    //��Ӧ����
    public void respondRequest() {

        Request request = new Request(client);
        Responder responder = new Responder(client);

        Servlet servlet = WebApp.getServletByUrl(request.getUrl());     //ʹ��XML����
//        ����Ҫ�������Ϊͨ��XML�����ļ�����չServlet����϶ȵ�
//        Ҫ��ʹ��if�ķ��������Ժ�ÿ���һ��Servlet���Ҫ���޸Ĵ���
//        if(request.getUrl().equals("login")){
//            servlet = new LoginServlet();
//        } else if (request.getUrl().equals("register")) {
//            servlet = new RegisterServlet();
//        }else{
//            //��ת����ҳ������ҳ�棬Ŀǰ��δѧϰ���֪ʶ��֮���ٲ������ƹ���
//            servlet = new HomepageServlet();
//        }


        //���������״̬�룬ʣ�µ�������������responseRequest()
        if (null == servlet) {
            servlet = new NotFoundServlet();

            servlet.service(request, responder);

            //��ȡerror.html�ļ��������ͻ���
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
