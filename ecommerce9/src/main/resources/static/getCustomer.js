$(document).ready(function() {

			// GET REQUEST
			$("#showCustomerData").click(function(event) {

//                alert("Customer Data")

				event.preventDefault();

				getCustomerData();

			});

			// DO GET
			function getCustomerData() {

				$.ajax({

					type : "GET",

					url : window.location +"listCustomers",

					success : function(result) {

					console.log(result);

                    $.each(result, function(i, customer){

                    var customerList="<tr><td>"+(i+1)+"</td><td>"+customer.userId+"</td><td>"+customer.email+"</td><td>"+customer.is_active+"</td><td>"+customer.firstName+"</td><td>"+customer.middleName+"</td><td>"+customer.lastName+"</td><td>"+customer.contact+"</td>"

                    if(customer.is_active==true){

                        var btnLogic="<td><div><button class='btn btn-success' onclick='deActivateCustomerAccount(this)' type='button'>De-Activate</button><br><br></div></td></tr>"

                     }

                     else{

                         var btnLogic="<td><div><button class='btn btn-outline-warning' onclick='activateCustomerAccount(this)' type='button'>Activate</button><br><br></div></td></tr>"

                     }

                    $('#customerData').append(customerList+btnLogic)

                 });
              }
         });
     }
})



function findIdValue(obj){

     console.log(obj.parentNode.parentNode.parentNode.cells[1].innerHTML);

     var id=obj.parentNode.parentNode.parentNode.cells[1].innerHTML;

     return id;

}



function deActivateCustomerAccount(obj){

     var id=findIdValue(obj);

     var urlPath="user/de-activate-account/";

     doEditAccount(id,urlPath);

    }



function activateCustomerAccount(obj){

     var id=findIdValue(obj);

     var urlPath="user/activate-account/";

     doEditAccount(id,urlPath);

    }



function doEditAccount(id,urlPath){

      $.ajax({
                  type : "PUT",

                  contentType : "application/json",

                  url  : window.location + urlPath + id,

                  data : JSON.stringify(id),

                  dataType : 'json',

                  success : function(result) {

                          console.log(result);

                          alert("Successfully updated.....");

                  },

                  error : function(e) {

                    alert("Not Updated.....")

                    console.log("ERROR: ", e);

                  }
        });
}