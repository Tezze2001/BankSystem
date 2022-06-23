const init = () => {
    document.getElementById('out').style.display = 'none'
} 

const validate = (idSender, idReceiver, amount) => {
    return checkId(idSender) && 
        checkId(idReceiver) &&
        checkAmount(amount)
}

const out = (msg) => {
    outDispalyMsg(msg, 'out')
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
            out('Input not valid')
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
                throw new Error("Invalid ids or amount");
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            out('Transaction id: ' + data.idTransaction + '<br>sender id: ' + data.sender.id + '<br>sender balance: ' + data.sender.balance + '<br>receiver id: ' + data.receiver.id + '<br>receiver balance: ' + data.receiver.balance)
        })
        .catch(function(error) {
            console.log(error);
            out(error)
        });
    })
    
}