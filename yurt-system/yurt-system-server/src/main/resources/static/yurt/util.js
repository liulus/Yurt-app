(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
        typeof define === 'function' && define.amd ? define(factory) :
            (global = global || self, global.util = factory());
}(this, function () {
    function request(method, url, params, isJson) {
        if (url.indexOf('/') !== 0) {
            url = '/' + url
        }
        var headers = new Headers();
        headers.append('Accept', 'application/json, text/plain, */*')
        var accessToken = localStorage.getItem('access_token')
        if (accessToken) {
            headers.append('Authorization', 'bearer ' + accessToken)
        }

        var requestConfig = {
            method: method,
            credentials: 'omit',
            headers: headers
        }
        if (params) {
            if (method === 'get') {
                url = url + '?' + formParam(params)
            } else {
                headers.append('Content-Type', isJson ? 'application/json;charset=UTF-8' : 'application/x-www-form-urlencoded')
                requestConfig.body = isJson ? JSON.stringify(params) : formParam(params)
            }
        }
        return fetch(contextPath + url, requestConfig)
            .then(res => (res.headers.get("content-type") || '').indexOf('application/json') >= 0 ? res.json() : res.text())
            .then(res => res.code == 'UN_LOGIN' ? window.location.href = '/login' : res)
            .then(res => {
                if (res.code == 'ACCESS_DENIED') {
                    window.app.$message.error(res.message)
                }
                return res
            })
    }

    function formParam(params) {
        var paramArray = [];
        for (var key in params) {
            if (params[key]) {
                paramArray.push(key + '=' + params[key])
            }
        }
        return paramArray.join('&')
    }

    return {
        http: {
            get: function (url, params) {
                return request('get', url, params, false)
            },
            postForm: function (url, params) {
                return request('post', url, params, false)
            },
            postJson: function (url, params) {
                return request('post', url, params, true)
            },
            putForm: function (url, params) {
                return request('put', url, params, false)
            },
            putJson: function (url, params) {
                return request('put', url, params, true)
            },
            delete: function (url, params) {
                return request('delete', url, params)
            }
        }

    }
}))







