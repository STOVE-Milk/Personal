const managementServerUri = "http://localhost:8080/management"

let getUserDatas = (page) => {
    let url = managementServerUri + "/users?page=" + (page > 0 ? page : 0)
    let method = "GET"
    let success = (result) => {
        console.log(result)
        //resetTable()
        updateTable(result.users)
    }
    let error = (e) => {
        console.log(e)
        alert(e.status)
    }
    doAjax(url,method,null ,success,error,null)
}

let updateTable = (datas) => {
    let table = document.getElementById("user-list-table")
    for(let i = 1; i <= datas.length; i++) {
        console.log(datas[i - 1])
        let newRow = table.insertRow(i);
        let user = datas[i-1];

        let nicknameCell = newRow.insertCell(0)
        let emailCell = newRow.insertCell(1)
        let roleCell = newRow.insertCell(2)
        let changeRoleCell = newRow.insertCell(3)
        let verifiedCell = newRow.insertCell(4)
        //let deleteUserCell = newRow.insertCell(5)

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