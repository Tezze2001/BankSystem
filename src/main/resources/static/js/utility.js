const checkId = (id) => {
    let regex = new RegExp('^[0-9a-f]{20}$')
    return regex.test(id)
}

const checkAmount = (amount) => {
    let regex = new RegExp('^([1-9][0-9]*\.[0-9]+)|([0-9]+)$')
    return regex.test(amount)
}

const checkNameSurname = (str) => {
    let regex = new RegExp('^([a-zA-Z])+$')
    return regex.test(str)
}


const outDispalyMsg = (msg, idHtmlElement) => {
    const out = document.getElementById(idHtmlElement)
    out.innerHTML = msg
    out.style.display = 'block'
} 