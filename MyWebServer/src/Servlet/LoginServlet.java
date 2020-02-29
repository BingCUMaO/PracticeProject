package Servlet;

import Core.Request;
import Core.Responder;

public class LoginServlet extends Servlet {


    @Override
    public void service(Request request, Responder responder) {
        this.request = request;
        this.responder = responder;

        //·µ»ØµÄÒ³Ãæ
        StringBuffer content = new StringBuffer();
        content.append("<html>");
        content.append("<head>");
        content.append("<title>");
        content.append("Login Successfully!");
        content.append("</title>");
        content.append("</head>");
        content.append("<body>");
        content.append("Return Datas Successfully!");
        content.append("</body>");
        content.append("</html>");

        this.responder.print(content.toString());
    }

    @Override
    public void doGET(Request request, Responder responder) {
    }

    @Override
    public void doPOST(Request request, Responder responder) {
    }


}
