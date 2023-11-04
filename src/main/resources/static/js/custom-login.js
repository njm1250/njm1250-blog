document.addEventListener('DOMContentLoaded', function() {
  // 로그인 폼에 대한 참조를 가져옵니다.
  const loginForm = document.getElementById('loginForm');

  if (loginForm) {
    // 폼 제출 이벤트에 대한 리스너를 추가합니다.
    loginForm.addEventListener('submit', function(event) {
      event.preventDefault();

      // 입력된 사용자 이름과 비밀번호를 가져옵니다.
      const username = document.getElementById('username').value;
      const rawPassword = document.getElementById('password').value;

      // 서버로 로그인 요청을 보냅니다.
      fetch('/api/v1/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: username,
          rawPassword: rawPassword
        }),
        credentials: 'include' // 쿠키를 포함한 요청을 보내려면 이 옵션이 필요합니다.
      })
      .then(response => {
        if (response.ok) {
          return response.text(); // 성공 시, 성공 메시지를 받습니다.
        } else {
          return response.text().then(text => { throw new Error(text) }); // 실패 시, 오류 메시지를 받습니다.
        }
      })
      .then(text => {
        alert('Login successful: ' + text);
        window.location.href = '/home'; // 로그인 성공 시 홈페이지로 리디렉션
      })
      .catch(error => {
        alert('Login failed: ' + error.message); // 오류 메시지를 출력합니다.
      });
    });
  }
});
