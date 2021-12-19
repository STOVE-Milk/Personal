const authServerUri = "http://localhost:8080/auth"

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
        data: data ? JSON.stringify(data) : null,
        dataType: "json",
        beforeSend: (xhr) => {
            xhr.setRequestHeader ("Authorization", getAccessToken())
        },
        success: success,
        error: error ? error : (e) => {
            console.log(e)
        },
        complete: complete
    })
}

let setCookie = (name, value, exp) => {
    let date = new Date(exp);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + "; domain=localhost; path=/"
    console.log(document.cookie)
}

let getAccessToken = () => {
    if(document.cookie == null)
        return "";
    return "Bearer " + document.cookie.split(';')[0].split('=')[1]
}

let login = () => {
    let url = authServerUri + "/login"
    let method = "POST"
    let data = {
        "email": $("#email").val(),
        "password": $("#password").val()
    }
    let success = (response) => {
        console.log("success")
        console.log(response)
        // console.log(status)
        // console.log(xhr)
        // xhr.getResponseHeader('Set-Cookie');
        setCookie("accessToken", response.accessToken, response.exp)
        // await goBackPage()
    }
    let error = (error) => {
        alert(error)
    }
    let complete = (result) => {
        console.log(result)
    }

    doAjax(url, method, data, success, error, null)
}

let regist = () => {
    let url = authServerUri + "/regist"
    let method = "POST"
    let data = {
        "email": $("#email").val(),
        "password": $("#password").val(),
        "nickname": $("#nickname").val()
    }
    let success = (response) => {
        console.log("success")
        console.log(response)
    }
    let error = (error) => {
        alert(error)
    }
    let complete = (result) => {
        console.log(result)
    }

    doAjax(url, method, data, success, error, null)
}

let goBackPage = () => {
    queries = window.location.search.substr(1).split('&');
    if(queries.length > 0) {
        url = queries.split('=')[1];
        location.reload("url")
    }
    else
        location.reload('/')
}