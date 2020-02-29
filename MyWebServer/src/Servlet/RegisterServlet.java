package Servlet;


import Core.Request;
import Core.Responder;

public class RegisterServlet extends Servlet {

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

}
