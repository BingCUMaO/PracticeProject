package Core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 封装响应信息
 * 1、实现动态添加内容
 * 2、关注状态码，来拼接响应信息
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



    //1、准备内容(流模式)
    //2、获取字节数的长度
    public Responder print(String info) {
        content.append(info);
        //计算content的字节长度
        lenOfContent += info.getBytes().length;
        return this;
    }

    public Responder println(String info) {
        content.append(info).append(CRLF);
        //计算content的字节长度
        lenOfContent += (info+CRLF).getBytes().length;
        return this;
    }

    //3、拼接响应协议（注意空格与换行）
    private void createHeadInfo(int statusCode) {
        String stateDescription = "";
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(statusCode).append(BLANK);

        if(headInfo==null)
            statusCode = 505;       //状态码505

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

    //4、使用输出流输出,发送响应消息
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
