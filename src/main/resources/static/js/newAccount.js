const init = () => {
    document.getElementById('error').style.display = 'none'
} 

const validate = (name, surname) => {
    return checkNameSurname(name) && 
        checkNameSurname(surname)
}

const error = (msg) => {
    errorDispalyMsg(msg, 'error')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const name = document.getElementById('name')
    const surname = document.getElementById('surname')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(name.value, surname.value)) {
            error('error input not valid')
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