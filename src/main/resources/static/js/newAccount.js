const init = () => {
    document.getElementById('out').style.display = 'none'
} 

const validate = (name, surname) => {
    return checkNameSurname(name) && 
        checkNameSurname(surname)
}

const out = (msg) => {
    outDispalyMsg(msg, 'out')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const name = document.getElementById('name')
    const surname = document.getElementById('surname')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(name.value, surname.value)) {
            out('Name or surname not valid')
            return;
        }
        
        fetch('/api/account', {
            method : "POST",
            mode: 'cors',
            headers: {
                'Accept': 'application/json',
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "name": name.value,
                "surname": surname.value
            })
        })
        .then((response) => {
            if (response.status === 400) {
                throw new Error('Name or surname not setted');
            }
            if (!response.ok) {
                throw new Error();
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            out("id: " + data.id.id)
        })
        .catch(function(error) {
            out(error)
        });
    })
    
}