const init = () => {
    document.getElementById('error').style.display = 'none'
} 

const validate = (idSender, idReceiver, amount) => {
    return checkId(idSender) && 
        checkId(idReceiver) &&
        checkAmount(amount)
}

const error = (msg) => {
    errorDispalyMsg(msg, 'error')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const sender = document.getElementById('idAccountSender')
    const receiver = document.getElementById('idAccountReceiver')
    const amount = document.getElementById('amount')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(sender.value, receiver.value, amount.value)) {
            error('error id not valid')
            return;
        }
        
        fetch('/api/transfer/', {
            method : "POST",
            mode: 'cors',
            headers: {
                'Accept': 'application/json',
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "from": sender.value,
                "to": receiver.value,
                "amount": amount.value,
            })
        })
        .then((response) => {
            if (!response.ok) {
                error('Error')
                throw new Error();
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
        })
        .catch(function(error) {
            console.log(error);
        });
    })
    
}