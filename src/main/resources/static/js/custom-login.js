document.addEventListener('DOMContentLoaded', function() {
  const loginForm = document.getElementById('loginForm');

  if (loginForm) {
    loginForm.addEventListener('submit', function(event) {
      event.preventDefault();

      const username = document.getElementById('username').value;
      const rawPassword = document.getElementById('password').value;

      fetch('/api/v1/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: username,
          rawPassword: rawPassword
        }),
        credentials: 'include' // 쿠키를 포함한 요청을 보내려면 이 옵션이 필요
      })
      .then(response => {
        if (response.ok) {
          return response.text();
        } else {
          return response.text().then(text => { throw new Error(text) });
        }
      })
      .then(text => {
        alert('로그인 성공');
        window.location.href = '/home';
      })
      .catch(error => {
        alert('로그인 실패: ' + error.message);
      });
    });
  }
});
