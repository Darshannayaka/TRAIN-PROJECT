//first request - to server create order

const paymentStart = () => {
    console.log("payment started..");
    let amount = $("#payment_field").val();
    console.log(amount);
    if(amount=='' || amount==null){
        // alert("amount is required !!");
        swal("Failed !!", "amount is required !!", "error");
        return ;
    }

    //code for server request
    //we will use ajax to send request to server to create order-jquery

    $.ajax({
        url:"/user/bookTrainTicket",
        data:JSON.stringify({amount: amount,info:"order_request"}),
        contentType:"application/json",
        type:"POST",
        dataType:"json",
        success:function(response){
            //invoked when success
            console.log(response);
            if(response.status=="created"){
                //open payment form
                let options={
                    key:"rzp_test_gYB3Ndb3zXZh97",
                    amount:response.amount,
                    currency:"INR",
                    name:"INDIAN RAILWAYS",
                    description:"Ticket Price",
                    image:"https://images.unsplash.com/photo-1535535112387-56ffe8db21ff?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1174&q=80",
                    order_id:response.id,
                    handler:function(response){
                        console.log(response.razorpay_payment_id);
                        console.log(response.razorpay_order_id);
                        console.log(response.razorpay_signature);
                        console.log("payment Successful !!!");
                        // alert("Congrats !! Payment is Successful !!!");

                        updatePaymentOnServer(response.razorpay_payment_id,response.razorpay_order_id,"paid");

                        swal("Ticket Booked!", "Congrats !! Payment is Successful !!!", "success");
                    },
                     prefill: {
                      name: "",
                     email: "",
                    contact: "",
                    },
                    
                    notes:{
                        address:"railway ticket booking",
                    },

                    theme:{
                        color: "#3399cc",
                    },
                };

                let rzp = new Razorpay(options);
                // var rzp = new Razorpay(options);
            rzp.on("payment.failed", function (response){
                console.log(response.error.code);
                console.log(response.error.description);
                console.log(response.error.source);
                console.log(response.error.step);
                console.log(response.error.reason);
                console.log(response.error.metadata.order_id);
                console.log(response.error.metadata.payment_id);
                // alert("Oops payment failed!!");
                swal("Failed !!", "Oops payment failed!!", "error");
            });
                rzp.open();
            }

        },
        error:function(error){
            //invoked when error
            console.log(error);
            alert("something went wrong !!");
        },
    });

};

//function
function updatePaymentOnServer(payment_id,order_id,status){
    $.ajax({
        url:"/user/update_order",
        data:JSON.stringify({payment_id: payment_id,
            order_id: order_id,
            status: status,
        }),
        contentType:"application/json",
        type:"POST",
        dataType:"json",
        success:function(response){
            //success message
            swal("Ticket Booked!", "Congrats !! Payment is Successful !!!", "success");
        },
        error:function(error){
            swal("Failed !!", "Your payment is successful, but we did not get on server, we will contact you as soon as possible", "error");
        },
    });
}