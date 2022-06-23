const init = () => {
    document.getElementById('out').style.display = 'none'
} 

const validate = (id) => {
    return checkId(id)
}

const out = (msg) => {
    outDispalyMsg(msg, 'out')
} 

window.onload = () => {
    const submit = document.getElementById('submit')
    const id = document.getElementById('idAccount')
    init()

    submit.addEventListener('click', () => {    
        init()
        if (!validate(id.value)) {
            out('id not valid')
            return;
        }
        
        fetch('/api/account?id=' + id.value, {
            method : "DELETE",
            mode: 'cors',
            headers: {
                'Accept': 'application/json',
                "Content-Type" : "application/json"
            }
        })
        .then((response) => {
            if (response.status === 400) {
                throw new Error('Id not valid');
            }
            if (!response.ok) {
                throw new Error();
            }
            return response;
        })
        .then(data => {
            console.log('deleted')
            out('deleted')
        })
        .catch(function(error) {
            console.log(error);
            out(error)
        });
    })
    
}