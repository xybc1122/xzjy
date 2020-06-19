/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function get(url, params={}){
    return new Promise((resolve, reject) =>{
        axios.get(url, {
            params: params
        }).then(res => {
            resolve(res.data);
        }).catch(err =>{
            reject(err.data)
        })
    });}

/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function post(url, params) {
    return new Promise((resolve, reject) => {
        axios.post(url, params)
            .then(res => {
                resolve(res.data);
            })
            .catch(err =>{
                console.log(err)
                reject(err.data)
            })
    });
}



function exportExcel(url, method='get',params={}){
    return new Promise((resolve, reject) =>{
        axios({
            url: url, method: method,
            responseType: 'blob', data: params
        }).then(res=>{
            resolve(res.data);
        }).catch(err =>{
            reject(err.data)
        })
    });}
