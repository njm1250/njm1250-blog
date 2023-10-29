function registerUser() {
    const username = document.getElementById('username').value;
    const rawPassword = document.getElementById('password').value;

    fetch('/api/v1/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            rawPassword: rawPassword
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(message => {
        alert(message); // "User created successfully" 출력
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error occurred while registering.');
    });
}
