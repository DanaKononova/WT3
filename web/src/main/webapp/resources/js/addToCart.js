function addToCart(jewelryId) {
    const quant = document.querySelector("#quantity" + jewelryId);
    const quantityErrorStatus = document.querySelector("#statusMessage" + jewelryId);
    const message = document.querySelector("#statusMessage");
    const messageHead = document.querySelector("#statusMessageHead");
    const messageBody = document.querySelector("#statusMessageBody");
    $.ajax({
        type: "POST",
        url: contextPath + "ajaxCart",
        data: JSON.stringify({jewelryId, quantity : quant.value}),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.errorStatus == false) {
                message.className = "panel panel-success";
                messageHead.innerText = "Success";
                quantityErrorStatus.innerHTML = "";
            }
            else {
                message.className = "panel panel-danger";
                messageHead.innerText = "Error";
                quantityErrorStatus.innerHTML = data.message;
            }
            messageBody.innerText = data.message;
            $("#cartTotalQuantity").text(data.totalQuantity);
            $("#cartTotalCost").text(data.totalCost);
        },
        error: function (data) {
            message.className = "panel panel-danger";
            messageHead.innerText = "Error";
            quantityErrorStatus.innerHTML = "There was an error";
            messageBody.innerText = "There was an error";
        }
    });
    message.hidden = false;
}
