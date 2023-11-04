document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('a_username').style.display = 'none';
    document.getElementById('btn_login').style.display = 'block';
    // 로그인 상태 확인
    fetch('/api/v1/users/status', {
        credentials: 'include' // 쿠키를 포함한 요청을 보내려면 이 옵션이 필요
    })
    .then(response => response.json())
    .then(data => {
        if (data.loggedIn) {
            document.getElementById('a_username').textContent = data.username;
            document.getElementById('a_username').style.display = 'block';
            document.getElementById('btn_login').style.display = 'none';
            document.getElementById('btn_logout').style.display = 'block';
            document.getElementById('writePost').style.display = 'block';
            if (data.isAdmin) {
                document.getElementById('writePost').style.display = 'block';
            } else {
                document.getElementById('writePost').style.display = 'none';
            }
        } else {
            document.getElementById('a_username').style.display = 'none';
            document.getElementById('btn_login').style.display = 'block';
            document.getElementById('btn_logout').style.display = 'none';
            document.getElementById('writePost').style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Error fetching login status:', error);
    });
});

document.addEventListener('DOMContentLoaded', function() {
    // 글쓰기 버튼 클릭 이벤트 핸들러
    document.getElementById('writePost').addEventListener('click', function() {
        var form = document.getElementById('postForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    });

    // 폼 제출 이벤트 핸들러
    document.querySelector('form').onsubmit = function(event) {
        event.preventDefault();
        submitPost();
    };
});

function logout() {
    fetch('/api/v1/users/logout', {
        method: 'POST',
        credentials: 'include' // 쿠키를 포함한 요청을 보내려면 이 옵션이 필요합니다.
    })
    .then(response => {
        if (response.ok) {
            // 로그아웃 성공 시, UI 업데이트 및 리다이렉트
            alert('로그아웃 되었습니다.');
            window.location.href = '/home'; // 로그인 페이지로 리다이렉트
        } else {
            throw new Error('로그아웃 처리에 실패하였습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

