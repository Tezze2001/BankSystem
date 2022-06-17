const checkId = (id) => {
    let regex = new RegExp('^([0-9a-f]){8}(-([0-9a-f]){4}){3}-([0-9a-f]){12}$')
    return regex.test(id)
}

const checkAmount = (amount) => {
    let regex = new RegExp('^([1-9][0-9]*\.[0-9]+)|([0-9]+)$')
    return regex.test(amount)
}

const errorDispalyMsg = (msg, idHtmlElement) => {
    const error = document.getElementById(idHtmlElement)
    error.innerHTML = msg
    error.style.display = 'block'
} 