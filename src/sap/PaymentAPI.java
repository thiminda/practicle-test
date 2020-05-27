package sap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class PaymentAPI extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	Payment Paymentobj = new Payment();
 /**
  * @see HttpServlet#HttpServlet()
  */
    public PaymentAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output =Paymentobj.insertpayment(request.getParameter("paymentID"),
				 request.getParameter("paymentDate"),
				request.getParameter("paymentMethod"),
				request.getParameter("paymentDueDate"),
		         request.getParameter("apt_ID"));
		
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		Map paras = getParasMap(request);
		
		String output = Paymentobj.updatepaymant(paras.get("hidpaymentIDSave").toString(), 
				paras.get("paymentDate").toString(),
				paras.get("paymentMethod").toString(), 
				paras.get("paymentDueDate").toString(), 
				paras.get("apt_ID").toString());
				
		
		response.getWriter().write(output);
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		 String output = Paymentobj.deletepayment(paras.get("paymenID").toString());
		  	 
		response.getWriter().write(output); 

		// TODO Auto-generated method stub
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
			
			for (String param : params)
				{

				String[] p = param.split("=");
				map.put(p[0], p[1]);
				}
			} 
		catch (Exception e) {
	}
		return map;
	}

}
