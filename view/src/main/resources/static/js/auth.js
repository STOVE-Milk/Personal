const apiGatewayURI = "http://localhost:8080/api"

let doAjax = (url, method, data, success, error, complete) => {
    //storage에서 쿠키 가져오기
    $.ajax({
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        url: url,
        type: method,
        contentType: "application/json",
        data: data,
        dataType: "json",
        success: success,
        error: error,
        complete: complete
    })
}

let login = () => {
    let url = apiGatewayURI + "/auth/login"
    let method = "POST"
    let data = {
        "email": $("#email").val(),
        "password": $("#password").val()
    }
    let success = (response) => {
        console.log("success")
        console.log(response)
    }
    let error = (error) => {
        console.log(error)
    }
    let complete = (result) => {
        console.log(result)
    }

    doAjax(url, method, data, success, error, complete)
}