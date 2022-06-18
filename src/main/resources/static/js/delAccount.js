const init = () => {
    document.getElementById('error').style.display = 'none'
} 

const validate = (id) => {
    return checkId(id)
}

const error = (msg) => {
    errorDispalyMsg(msg, 'error')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const id = document.getElementById('idAccount')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(id.value)) {
            error('error id not valid')
            return;
        }
        
        fetch('/api/account', {
            method : "DELETE",
            mode: 'cors',
            headers: {
                'Accept': 'application/json',
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "id": id.value
            })
        })
        .then((response) => {
            if (!response.ok) {
                error('Error id not found')
                throw new Error();
            }
            return response;
        })
        .then(data => {
            console.log("Id deleted")
        })
        .catch(function(error) {
            console.log(error);
        });
    })
    
}