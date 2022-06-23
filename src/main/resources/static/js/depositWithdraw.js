const init = () => {
    document.getElementById('out').style.display = 'none'
} 

const validate = (id, amount) => {
    return checkId(id) && checkAmount(amount)
}

const out = (msg) => {
    outDispalyMsg(msg, 'out')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const id = document.getElementById('idAccount')
    const amount = document.getElementById('amount')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(id.value, amount.value)) {
            out('error input not valid')
            return;
        }
        
        fetch('/api/account/' + id.value, {
            method : "POST",
            mode: 'cors',
            headers: {
                'Accept': 'application/json',
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "amount": amount.value
            })
        })
        .then((response) => {
            if (response.status === 404) {
                new Error('Account not found')
            } 
            if (response.status === 400) {
                new Error('Inputs not valid')
            }
            if (!response.ok) {
                throw new Error()
            }
            return response.json()
        })
        .then(data => {
            console.log("id of deposit or withdraw: ")
            console.log(data) 
            out('Transaction id: ' + data.idTransaction + '<br>account balance: ' + data.balance)
        })
        .catch(function(error) {
            console.log(error)
            out(error)
        });
    })
    
}