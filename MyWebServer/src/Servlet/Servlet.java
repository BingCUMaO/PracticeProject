package Servlet;

import Core.Request;
import Core.Responder;

/**
 * ������С�ű�
 */
public abstract class Servlet {
	protected Request request;
	protected Responder responder;

	public abstract void service(Request request, Responder responder);

	public abstract void doGET(Request request, Responder responder);

	public abstract void doPOST(Request request, Responder responder);

	public void responseRequest(int statusCode){
		responder.responseBrowser(statusCode);
	}
}
