package Core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * ��װ��Ӧ��Ϣ
 * 1��ʵ�ֶ�̬�������
 * 2����ע״̬�룬��ƴ����Ӧ��Ϣ
 */
public class Responder {
    private BufferedWriter bw;
    private StringBuilder content;
    private StringBuilder headInfo;
    private int lenOfContent;

    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";


    private Responder() {

        content = new StringBuilder();
        headInfo = new StringBuilder();
        lenOfContent = 0;
    }

    public Responder( Socket client){
        this();
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            this.headInfo = null;
        }
    }

    public Responder(OutputStream os){
        this();
        this.bw = new BufferedWriter(new OutputStreamWriter(os));
    }



    //1��׼������(��ģʽ)
    //2����ȡ�ֽ����ĳ���
    public Responder print(String info) {
        content.append(info);
        //����content���ֽڳ���
        lenOfContent += info.getBytes().length;
        return this;
    }

    public Responder println(String info) {
        content.append(info).append(CRLF);
        //����content���ֽڳ���
        lenOfContent += (info+CRLF).getBytes().length;
        return this;
    }

    //3��ƴ����ӦЭ�飨ע��ո��뻻�У�
    private void createHeadInfo(int statusCode) {
        String stateDescription = "";
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(statusCode).append(BLANK);

        if(headInfo==null)
            statusCode = 505;       //״̬��505

        switch (statusCode) {
            case 200:
                stateDescription = "OK";
                break;
            case 404:
                stateDescription = "NOT FOUND";
                break;
            case 505:
                stateDescription = "SERVER ERROR";
                break;
        }
        headInfo.append(stateDescription).append(CRLF);

        headInfo.append("Data:").append(new Date()).append(CRLF);
        headInfo.append("Core.Server:").append("BinGCU Core.Server/0.0.1;charset=GBK").append(CRLF);
        headInfo.append("Content-type:").append("text/html").append(CRLF);
        headInfo.append("Content-length:").append(lenOfContent).append(CRLF);
        headInfo.append(CRLF);
    }

    //4��ʹ����������,������Ӧ��Ϣ
    public void responseBrowser(int statusCode){
        createHeadInfo(statusCode);
        try {
            bw.append(headInfo);
            bw.append(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (statusCode>=200&&statusCode<300) {
            System.out.println("------------------------------");
            System.out.println("Respond Successfully!");
        }
    }


}
