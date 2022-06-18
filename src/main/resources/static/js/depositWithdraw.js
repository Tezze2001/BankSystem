const init = () => {
    document.getElementById('error').style.display = 'none'
} 

const validate = (id, amount) => {
    return checkId(id) && checkAmount(amount)
}

const error = (msg) => {
    errorDispalyMsg(msg, 'error')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const id = document.getElementById('idAccount')
    const amount = document.getElementById('amount')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(id.value, amount.value)) {
            error('error input not valid')
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
            if (!response.ok) {
                error('Error id not found')
                throw new Error();
            }
            return response.json();
        })
        .then(data => {
            console.log("id of deposit or withdraw: " + data) 
        })
        .catch(function(error) {
            console.log(error);
        });
    })
    
}