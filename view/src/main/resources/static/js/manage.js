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
        data: JSON.stringify(data),
        dataType: "json",
        success: success,
        error: error,
        complete: complete
    })
}

let getUserDatas = (page) => {
    let url = "http://localhost:8080/api/user"
    let method = "GET"
    let data = {
        page: page
    }
    let success = async (result) => {
        await resetTable()
        await updateTable(result)
    }
    doAjax(url,method,data,success,null,null)
}

let updateTable = (datas) => {
    let table = document.getElementById("user-list-table")
    for(let i = 1; i <= datas.length; i++) {

        let newRow = table.insertRow(i);
        let user = datas[i-1];

        let nicknameCell = newRow.insertCell(0)
        let emailCell = newRow.insertCell(1)
        let roleCell = newRow.insertCell(2)
        let verifiedCell = newRow.insertCell(3)

        nicknameCell.innerText = user.nickname;
        emailCell.innerText = user.email;
        roleCell.innerText = user.role;
        verifiedCell.innerText = user["verified"];
    }
}

let resetTable = () => {
    let table = document.getElementById("user-list-table")
    while(1) {
        try {
            table.deleteRow(1);
        }
        catch (e) {
            break;
        }
    }
}