package Servlet;

import MyWebServer.Request;
import MyWebServer.Responder;

/**
 * ������С�ű�
 */
public interface Servlet {
	void service(Request request, Responder responder);

	void doGET(Request request, Responder responder);

	void doPOST(Request request, Responder responder);

	void responseRequest(int statusCode);
}
