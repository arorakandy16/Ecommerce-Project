$(document).ready(function() {

			// GET REQUEST
			$("#showSellerData").click(function(event) {

//                alert("Seller Data")

				event.preventDefault();

				getSellerData();

			});

			// DO GET
			function getSellerData() {

				$.ajax({

					type : "GET",

					url : window.location +"listSellers",

					success : function(result) {

					console.log(result);

                    $.each(result, function(i, seller){

                    var sellerList="<tr><td>"+(i+1)+"</td><td>"+seller.userId+"</td><td>"+seller.email+"</td><td>"+seller.is_active+"</td><td>"+seller.firstName+"</td><td>"+seller.middleName+"</td><td>"+seller.lastName+"</td><td>"+seller.companyContact+"</td><td>"+seller.companyName+"</td>"

                    if(seller.is_active==true){

                        var btnLogic="<td><div><button class='btn btn-success' onclick='deActivateSellerAccount(this)' type='button'>De-Activate</button><br><br></div></td></tr>"

                     }

                     else{

                         var btnLogic="<td><div><button class='btn btn-outline-warning' onclick='activateSellerAccount(this)' type='button'>Activate</button><br><br></div></td></tr>"

                     }

                    $('#sellerData').append(sellerList+btnLogic)

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


function deActivateSellerAccount(obj){

     var id=findIdValue(obj);

     var urlPath="user/de-activate-account/";

     doChangeAccount(id,urlPath);

    }



function activateSellerAccount(obj){

     var id=findIdValue(obj);

     var urlPath="user/activate-account/";

     doChangeAccount(id,urlPath);

    }



function doChangeAccount(id,urlPath){

      $.ajax({
                  type : "PUT",

                  contentType : "application/json",

                  url  : window.location + urlPath + id,

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