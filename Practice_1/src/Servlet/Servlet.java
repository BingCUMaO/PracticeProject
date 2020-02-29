package Servlet;

import MyWebServer.Request;
import MyWebServer.Responder;

/**
 * 服务器小脚本
 */
public interface Servlet {
	void service(Request request, Responder responder);

	void doGET(Request request, Responder responder);

	void doPOST(Request request, Responder responder);

	void responseRequest(int statusCode);
}
