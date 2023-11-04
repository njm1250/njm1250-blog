function submitPost() {
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    console.log(title, content);
    return;

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
        if(response.ok) {
            return response.json(); // 서버가 JSON 응답을 반환하는 경우
        }
        throw new Error('Network response was not ok.');
    })
    .then(data => {
        console.log(data); // 성공적으로 데이터를 받으면 여기에서 처리
        // 예를 들어 게시글 목록 페이지로 리디렉션 하거나 성공 메시지를 표시할 수 있습니다.
    })
    .catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
        // 오류 처리 로직
    });
}