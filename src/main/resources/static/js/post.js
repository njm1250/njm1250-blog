function submitPost() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    fetch('/api/v1/blog/post', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            title: title,
            content: content
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
        alert("글 작성 완료");
        window.location.href = '/home';
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);  // 백엔드에서 반환한 유효성 검사 실패 메시지
    });
}