document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/v1/blog/getPosts') // 서버에 요청하는 URL
        .then(response => response.json()) // 응답을 JSON으로 파싱
        .then(posts => {
            const postsContainer = document.querySelector('.post-list');
            posts.forEach(post => {
                const date = new Date(post.writtenDate);
                const dateString = date.toLocaleString();
                const postElement = `
                    <a href="/post_detail?postId=${post.postId}">
                        <li class="post">
                            <div class="post-title">${post.title}</div>
                            <div class="post-summary">${post.content}</div>
                            <div class="post-metadata">작성자: ${post.username} | 작성일: ${dateString}</div>
                        </li>
                    </a>
                `;
                postsContainer.insertAdjacentHTML('beforeend', postElement);
            });
        })
        .catch(error => console.error('Error:', error));
});

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
    document.getElementById('writePost').addEventListener('click', function() {
        var form = document.getElementById('postForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    });

    document.querySelector('form').onsubmit = function(event) {
        event.preventDefault();
        submitPost();
    };
});

function logout() {
    fetch('/api/v1/users/logout', {
        method: 'POST',
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            alert('로그아웃 되었습니다.');
            window.location.href = '/home';
        } else {
            throw new Error('로그아웃 처리에 실패하였습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

