
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
        header: {
            "auth" :  getAccessToken()
        },
        beforeSend: (xhr) => {
            xhr.setRequestHeader("Authorization", getAccessToken())
            // console.log("xhr\n" + getAccessToken() + '\n')
            // console.log(xhr)
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
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + "; path=/"
    //domain=localhost;
    console.log(document.cookie)
}

let setCookieTest = (name, value, exp) => {
    let date = new Date(exp);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + "; path=/;" + "domain=localhost"
    console.log(document.cookie)
}

let getAccessToken = () => {
    if(document.cookie == null)
        return "";
    return "Bearer " + document.cookie.split(';')[0].split('=')[1]
}