const authServerUri = "http://localhost:8080/auth"

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
        //xhr.getResponseHeader('Set-Cookie');
        localStorage.setItem("accessToken", response.refreshToken)
        setCookie("accessToken", response.accessToken, response.exp)
        setCookieTest("test", response.accessToken, response.exp)
        // goBackPage()
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
    queries = window.location.search.substr(1)
    if(queries.indexOf("&") !== -1)
        queries = queries.split('&');
    if(queries.length > 0) {
        url = queries.split('=')[1];
        location.replace('http://localhost/' + url)
    }
    else
        location.replace('http://localhost/')
}