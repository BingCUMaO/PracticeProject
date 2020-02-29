package Servlet;

import MyWebServer.Request;
import MyWebServer.Responder;

public class RegisterServlet implements Servlet {

	private Request request;
	private Responder responder;

	@Override
	public void service(Request request, Responder responder) {
		this.request = request;
		this.responder = responder;

		this.responder.print("Register Successfully!");
	}

	@Override
	public void doGET(Request request, Responder responder){
		this.request = request;
		this.responder = responder;
	}

	@Override
	public void doPOST(Request request, Responder responder){
		this.request = request;
		this.responder = responder;
	}

	@Override
	public void responseRequest(int statusCode) {
		responseRegisterRequest(statusCode);
	}

	private void responseRegisterRequest(int statusCode){
		responder.responseBrowser(statusCode);
	}
}
