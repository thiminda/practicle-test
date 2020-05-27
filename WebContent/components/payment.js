console.log('Hello World')

$(document).ready(function()

 {
	 $("#alertSuccess").hide();
	 $("#alertError").hide();
 });

//SAVE........
$(document).on("click", "#btnSave", function(event)
{
	//Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide(); 


//Form validation-------------------
var status = validatepaymentForm();
if (status != true)
 {
	 $("#alertError").text(status);
	 $("#alertError").show();
 return;
 }  

//If valid------------------------
var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT"; 

$.ajax(
		{
		 url : "PaymentAPI",
		 type : type,
		 data : $("#formPayment").serialize(),
		 dataType : "text",
		 complete : function(response, status)
		 {
			 onPayementSaveComplete(response.responseText, status);
		 }
	});
});

function onPayementSaveComplete(response, status)
{   
	if (status == "success")
	 {
		 var resultSet = JSON.parse(response);
		 
		 if (resultSet.status.trim() == "success")
		 {
			 $("#alertSuccess").text("Successfully saved.");
			 $("#alertSuccess").show();
			 
			 $("#divPaymentsGrid").html(resultSet.data);
		 }	else if (resultSet.status.trim() == "error")
		 {
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
		 }
	 } else if (status == "error")
	 {
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while saving..");
		 $("#alertError").show();
	 }
		 $("#hidPaymenttIDSave").val("");
		 $("#formPayment")[0].reset(); 
} 

//remove

$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
			 url : "PaymentAPI",
			 type : "DELETE",
			 data : "apt_ID=" + $(this).data("apt_ID"),
			 dataType : "text",
			 complete : function(response, status)
			 {
				 onPaymentDeleteComplete(response.responseText, status);
			 }
		});
	});




function onPaymentDeleteComplete(response, status)
{
	if (status == "success")
	 {
		 var resultSet = JSON.parse(response);
		 if (resultSet.status.trim() == "success")
	 {
		 $("#alertSuccess").text("Successfully deleted.");
		 $("#alertSuccess").show();
		 $("#divItemsGrid").html(resultSet.data);
	 } else if (resultSet.status.trim() == "error")
	 {
		 $("#alertError").text(resultSet.data);
		 $("#alertError").show();
	 }
	 } else if (status == "error")
		 {
		 $("#alertError").text("Error while deleting.");
		 $("#alertError").show();
	 } else
	 {
		 $("#alertError").text("Unknown error while deleting..");
		 $("#alertError").show();
	 }
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidpaymentIDSave").val($(this).closest("tr").find('#hidpaymentIDUpdate').val());
 $("#paymentDate").val($(this).closest("tr").find('td:eq(0)').text());
 $("#paymentMethod").val($(this).closest("tr").find('td:eq(1)').text());
 $("#paymentDueDate").val($(this).closest("tr").find('td:eq(2)').text());
 $("#apt_ID").val($(this).closest("tr").find('td:eq(3)').text());
 
});

//CLIENT-MODEL================================================================
function validatePaymentForm()
{
// description
if ($("#paymentDate").val().trim() == "")
 {
 return "Insert paymentDate.";
 }
// patientId
if ($("#paymentMethod").val().trim() == "")
 {
 return "Insert paymentMethod.";
 }
// doctorId
if ($("#paymentDueDate").val().trim() == "")
 {
 return "Insert paymentDueDate.";
 }
// hospitalId
if ($("#apt_ID").val().trim() == "")
{
return "Insert apt_ID.";
}



return true;
}
