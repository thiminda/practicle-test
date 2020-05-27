<%@page import="sap.PaymentAPI"%>
<%@page import="sap.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1>Payment Management</h1>
				<form id="formpayment" name="formpayment" method="post" action="items.jsp">
					payment code: <input id="paymentid" name="paymentid" type="text"
						class="form-control form-control-sm"> <br> payment Date:
					<input id="paymentDate" name="paymentDate" type="text"
						class="form-control form-control-sm"> <br> payment
					Method: <input id="paymentMethod" name="paymentMethod" type="text"
						class="form-control form-control-sm"> <br> payment
					DueDate: <input id="paymentDueDate" name="paymentDueDate" type="text"
						class="form-control form-control-sm"> <br> apt_ID
					: <input id="apt_ID" name="apt_ID" type="text"
						class="form-control form-control-sm"> <br> 	
						<input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>

				<div id="alterSuccess" class="alert alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>

				<div id="alterError" class="alert alert-danger"></div>
				<br>
				<%
				        Payment Paymentobj = new Payment();
				        out.print(Paymentobj.readpayment());
				%>
			</div>
		</div>
	</div>


</body>
</html>