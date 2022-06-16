var fetch = (method, url, async, handler, body) => {
    var xReq = new XMLHttpRequest()
    xReq.onreadystatechange = handler
    xReq.open(method, url, async)
    xReq.send(body)
}

var head = (url, handler) => {
    fetch('head', url, true, handler, undefined);
}

export var get = (url, handler) => {
    fetch('get', url, true, handler, undefined);
}

var post = (url, handler, body) => {
    fetch('post', url, true, handler, body);
}

var put = (url, handler, body) => {
    fetch('put', url, true, handler, body);
}

var path = (url, handler, body) => {
    fetch('post', url, true, handler, body);
}

var del = (url, handler, body) => {
    fetch('delete', url, true, handler, body);
}

//export { head, get, post, put, path, del }