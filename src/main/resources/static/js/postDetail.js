function submitComment() {
    const postId = document.getElementById('postId').value;
    const commentText = document.getElementById('commentText').value;

    fetch('/api/v1/blog/comment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            postId: postId,
            commentText: commentText
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
        alert("댓글 등록 완료");
        window.location.reload();
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);  // 백엔드에서 반환한 유효성 검사 실패 메시지
    });
}