package MyWebServer;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

/**
 * ��װRequest����
 * ��ȡmethod uri�Լ�Request parameter
 */
public class Request {
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    private BufferedReader br;
    private String requestInfo;     //�����洢request����������
    private String method;         //���󷽷���GET��POST������
    private String url;                //�����url
    private String queryInfo;       //"?"����Ĳ�ѯ��Ϣ
    private LinkedHashMap<String, ArrayList<String>> parameterMap;

    private Request() {
        parameterMap = new LinkedHashMap<String, ArrayList<String>>();
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryInfo() {
        return queryInfo;
    }

    public BufferedReader getBr() {
        return br;
    }

    //���췽��
    public Request(Socket client) {
        this();

        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        char[] buf = new char[1024 * 1024];
        int len = 0;
        try {
            len = br.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail To Load Information!");
        }
        this.requestInfo = new String(buf, 0, len);

        extractRequestInfo();
    }

    //���췽��
    public Request(InputStream is) {
        this();

        this.br = new BufferedReader(new InputStreamReader(is));

        char[] buf = new char[1024 * 1024];
        int len = 0;
        try {
            len = br.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail To Load Information!");
        }
        this.requestInfo = new String(buf, 0, len);


        extractRequestInfo();
    }

    //��ȡrequest�����е�Ԫ��
    private void extractRequestInfo() {
        //������ʱ�������ȡ�ַ�����λ��ƫ��
        int off_start = 0;
        int off_end = requestInfo.indexOf(BLANK);
        method = requestInfo.substring(off_start, off_end).toUpperCase();

        off_start = off_end + 2;
        off_end = requestInfo.indexOf(BLANK + "HTTP/");
        url = requestInfo.substring(off_start, off_end);

        //��url����"/"��ȥ��
        if (url.lastIndexOf('/') == url.length() - 1) {
            url = url.substring(0, url.length() - 1);
        }

        //���Ϊ-1,���ʾ������Ϣ����parameters
        //����url��parameters����
        if (-1 != url.indexOf('?')) {
            off_start = url.indexOf('?') + 1;
            off_end = url.length();
            queryInfo = url.substring(off_start, off_end);

            off_start = 0;
            off_end = url.indexOf('?');
            url = url.substring(off_start, off_end);
        } else {
            queryInfo = "";
        }

        //���url�����ĵ�url���룬��ת��һ��
        try {
            queryInfo = ("" == queryInfo) ? "" : URLDecode(queryInfo, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //�������ʽΪPOST������Ҫ����������Body�е�parameters��ӽ�queryInfo
        if (method.equals("POST")) {
            //����POST�е�����URL����û�в�ѯ��Ϣ�����������"&"
            if (!queryInfo.equals(""))
                queryInfo += "&";

            queryInfo += requestInfo.substring(requestInfo.lastIndexOf(CRLF) + CRLF.length());
        }


        //��queryInfo����Ϣ���ֱ�ӳ�䵽parameterMap��
        String[] parameters = queryInfo.split("\\&");
        String[] KV;
        String key;
        String value = null;

        for (String parameter : parameters) {
            KV = parameter.split("=");
            key = KV[0];

            //����request�Ĳ���Ϊ"other="����û��value�����
            //����һ
            if (KV.length == 1) {
                value = "";
            } else {
                value = KV[1];
            }

            //������
//            KV = Arrays.copyOf(KV, 2);
//              if(KV==null)
//                    KV[1] = "";
//            value = KV[1];

            //Post��ʽ�е�request body�в���Ϊ���ģ�������δ�������
//            try {
//                value = (KV[1] == "") ? "" : decode(KV[1], "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            if (!parameterMap.containsKey(key)) {
                parameterMap.put(key, new ArrayList<String>(Arrays.asList(value)));
            } else {
                parameterMap.get(key).add(value);
            }

        }


    }

    public ArrayList<String> getParameterValue(String key) {
        return parameterMap.get(key);
    }

    public boolean containParameter(String parameter) {
        return parameterMap.containsKey(parameter);
    }

    public String URLDecode(String text, String charset) throws UnsupportedEncodingException {
        return URLDecoder.decode(text, charset);
    }

    public String getAllParameters(String separator) {
        StringBuilder result = new StringBuilder();
        for (String key : parameterMap.keySet()) {
            result.append(key).append(separator);
        }

        result.deleteCharAt(result.lastIndexOf(separator));
        return result.toString();
    }
}
