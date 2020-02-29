package Servlet;

import MyWebServer.Request;
import MyWebServer.Responder;

public class LoginServlet implements Servlet {

	private Request request;
	private Responder responder;

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
	public void responseRequest(int statusCode){
		responseLoginRequest(statusCode);
	}

	private void responseLoginRequest(int statusCode){
		responder.responseBrowser(statusCode);
	}

}
