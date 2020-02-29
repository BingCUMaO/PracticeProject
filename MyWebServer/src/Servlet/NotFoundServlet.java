package Servlet;

import Core.Request;
import Core.Responder;

/**
 * 该NotFoundServlet不需要添加到XML配置文件中
 */
public class NotFoundServlet extends Servlet {
    @Override
    public void service(Request request, Responder responder) {
        this.request = request;
        this.responder = responder;
    }

    @Override
    public void doGET(Request request, Responder responder) {

    }

    @Override
    public void doPOST(Request request, Responder responder) {

    }
}
