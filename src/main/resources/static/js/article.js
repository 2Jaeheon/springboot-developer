const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
  deleteButton.addEventListener('click', event => {
    let id = document.getElementById('article-id').value;

    // HTML에서 id를 delete-btn을 설정한 엘리먼트를 찾아서 그 엘리먼트에서 클릭 이벤트가
    // 발생하면 fetch 메서드를 통해 /api/articles/ 에 DELETE 요청을 보내는 역할을 함.
    // then은 fetch가 잘 완료되면 실행되는 메서드
    fetch(`/api/articles/${id}`, {
      method: 'DELETE'
    })
    .then(() => {
      alert('삭제가 완료되었습니다.');
      location.replace('/articles')
    })
  })
}

// 수정
// id가 modify-btn 엘리먼트를 찾고 그 엘리먼트에서 클릭 이벤트가 발생하면
// id가 title, content인 엘리먼트 값을 가져와 fetch() 메서드 통해 수정 API로 PUT 요청을 전송
// 요청을 보낼 때는 header에 요청 형식을, body에 HTML 입력한데이터를 JSON으로 바꿔서 전송
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
  modifyButton.addEventListener('click', event => {
    let params = new URLSearchParams(location.search);
    let id = params.get('id');

    fetch(`/api/articles/${id}`, {
      method: 'PUT', headers: {
        "Content-Type": "application/json",
      }, body: JSON.stringify({
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
      })
    })
    .then(() => {
      alert('수정이 완료되었습니다.');
      location.replace(`/articles/${id}`);
    });
  });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
  createButton.addEventListener('click', event => {
    fetch('/api/articles', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
      })
    })
    .then(() => {
      alert('등록 완료되었습니다.');
      location.replace('/articles');
    });
  });
}