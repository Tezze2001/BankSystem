const init = () => {
    document.getElementById('table-body').innerHTML = ''
    document.getElementById('Account-id').innerText = ''
    document.getElementById('Account-name').innerText = ''
    document.getElementById('Account-surname').innerText = ''
    document.getElementById('Account-balance').innerText = ''
    document.getElementById('out').style.display = 'none'
    document.getElementById('result').style.display = 'block'

} 

const out = (str) => {
    outDispalyMsg(str, 'out')
    document.getElementById('result').style.display = 'none'

} 

window.onload = () => {
    const submit = document.getElementById('submit_id')
    const id = document.getElementById('idAccountText')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!checkId(id.value)) {
            out('Id not valid')
            return;
        }
        
        fetch('/api/account/' + id.value, {
            method : "GET",
            mode: 'cors',
            headers: {}
        })
        .then((response) => {
            if (response.status === 404) {
                throw new Error("Account not found");
            }
            if (response.status === 400) {
                throw new Error("Id not valid");
            }
            if (!response.ok) {
                throw new Error();
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            document.getElementById('Account-id').innerText = id.value
            document.getElementById('Account-name').innerText = data.name
            document.getElementById('Account-surname').innerText = data.surname
            document.getElementById('Account-balance').innerText = data.balance + '€'
            data.transactions.forEach(element => {
                let receiver =  'none'
                let amount = element.amount
                if (element.type === "TRANSACTION") {
                    receiver = element.receiver.id.id
                    if (receiver !== id.value) {
                        amount *= -1
                    }
                }
                if (element.type === "WITHDRAW") {
                    amount *= -1
                }
                document.querySelector('tbody#table-body').innerHTML 
                        += '<tr><td>' + element.uuid + '</td><td>' + element.sender.id.id + '</td><td>' + receiver + '</td><td>' + amount +'€</td><td>' + element.time +'</td></tr>'
            });
            const first = document.querySelector('tbody#table-body>tr:first-child')
            if (first != undefined) {
                document.querySelector('tbody#table-body>tr:first-child').classList.add('fw-bold')
            }
        })
        .catch(function(error) {
            out(error)
        });
    })
    
}