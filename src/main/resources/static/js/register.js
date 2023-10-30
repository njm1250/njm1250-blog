function registerUser() {
    const username = document.getElementById('username').value;
    const rawPassword = document.getElementById('password').value;

    fetch('/api/v1/users/register', {
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
            return response.text().then(text => {
                throw new Error(text);
            });
        }
        return response.text();
    })
    .then(message => {
        alert(message);
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);  // 백엔드에서 반환한 유효성 검사 실패 메시지
    });
}
