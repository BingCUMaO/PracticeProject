package Servlet;

import Core.Request;
import Core.Responder;

/**
 * ��NotFoundServlet����Ҫ��ӵ�XML�����ļ���
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
