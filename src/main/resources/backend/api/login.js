
function loginApi(data,code) {
    return $axios({
        'url': '/login',
        'method': 'post',
        data: "/"+data+"/"+code
    })
}

function getCodeApi() {
    return $axios({
        'url': '/login/getCode',
        'method': 'get',
    })
}

function logoutApi(){
    return $axios({
        'url': '/login/logout',
        'method': 'post',
    })
}
