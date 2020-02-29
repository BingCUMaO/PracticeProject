package MyWebServer;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

/**
 * 封装Request请求
 * 获取method uri以及Request parameter
 */
public class Request {
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    private BufferedReader br;
    private String requestInfo;     //用来存储request的所有内容
    private String method;         //请求方法：GET、POST。。。
    private String url;                //请求的url
    private String queryInfo;       //"?"后面的查询信息
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

    //构造方法
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

    //构造方法
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

    //提取request请求中的元素
    private void extractRequestInfo() {
        //用来临时存放所读取字符串的位置偏移
        int off_start = 0;
        int off_end = requestInfo.indexOf(BLANK);
        method = requestInfo.substring(off_start, off_end).toUpperCase();

        off_start = off_end + 2;
        off_end = requestInfo.indexOf(BLANK + "HTTP/");
        url = requestInfo.substring(off_start, off_end);

        //将url最后的"/"给去掉
        if (url.lastIndexOf('/') == url.length() - 1) {
            url = url.substring(0, url.length() - 1);
        }

        //如果为-1,则表示请求消息中无parameters
        //否则将url与parameters分离
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

        //如果url有中文的url编码，需转化一下
        try {
            queryInfo = ("" == queryInfo) ? "" : URLDecode(queryInfo, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //如果请求方式为POST，则需要将藏在请求Body中的parameters添加进queryInfo
        if (method.equals("POST")) {
            //根据POST中的请求URL中有没有查询信息，来灵活地添加"&"
            if (!queryInfo.equals(""))
                queryInfo += "&";

            queryInfo += requestInfo.substring(requestInfo.lastIndexOf(CRLF) + CRLF.length());
        }


        //将queryInfo的信息，分别映射到parameterMap中
        String[] parameters = queryInfo.split("\\&");
        String[] KV;
        String key;
        String value = null;

        for (String parameter : parameters) {
            KV = parameter.split("=");
            key = KV[0];

            //考虑request的参数为"other="这种没有value的情况
            //方法一
            if (KV.length == 1) {
                value = "";
            } else {
                value = KV[1];
            }

            //方法二
//            KV = Arrays.copyOf(KV, 2);
//              if(KV==null)
//                    KV[1] = "";
//            value = KV[1];

            //Post方式中的request body中不能为中文，所以这段代码作废
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
