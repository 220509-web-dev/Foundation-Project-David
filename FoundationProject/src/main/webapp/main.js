window.onload = function() {
    document.getElementById("login-btn").addEventListener("click", login);
}

function login() {

    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch('/foundation/auth', {
        method: 'POST',
        body: JSON.stringify({username, password})
    }).then(resp => {
        if(resp.status == 204){
            console.log('You are now logged in!');
            document.getElementById("navbar-user").value = resp.username;
        } else{
            console.log("You failed to login!");
        }
    });

}