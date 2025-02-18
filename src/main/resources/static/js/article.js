// delete
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
  deleteButton.addEventListener('click', event => {
    let id = document.getElementById('article-id').value;

    function success() {
      alert('삭제가 완료되었습니다.');
      location.replace('/articles');
    }

    function fail() {
      alert('삭제 실패했습니다.');
      location.replace('/articles');
    }

    httpRequest('DELETE', `/api/articles/${id}`, null, success, fail);
  });
}

// update
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
  modifyButton.addEventListener('click', event => {
    let params = new URLSearchParams(location.search);
    let id = params.get('id');

    body = JSON.stringify({
      title: document.getElementById('title').value,
      content: document.getElementById('content').value
    })

    function success() {
      alert('수정 완료되었습니다.');
      location.replace(`/articles/${id}`);
    }

    function fail() {
      alert('수정 실패했습니다.');
      location.replace(`/articles/${id}`);
    }

    httpRequest('PUT', `/api/articles/${id}`, body, success, fail);
  });
}

// create
const createButton = document.getElementById('create-btn');

if (createButton) {
  // When you click the register button, a request is sent to /api/articles.
  createButton.addEventListener('click', event => {
    body = JSON.stringify({
      title: document.getElementById('title').value,
      content: document.getElementById('content').value
    });

    function success() {
      alert('등록 완료되었습니다.');
      location.replace('/articles');
    }

    function fail() {
      alert('등록 실패했습니다.');
      location.replace('/articles');
    }

    httpRequest('POST', '/api/articles', body, success, fail)
  });
}

// logout
const logoutButton = document.getElementById('logout-btn');

if (logoutButton) {
  logoutButton.addEventListener('click', event => {
    function success() {
      // Delete access tokens stored in local storage
      localStorage.removeItem('access_token');

      // Delete refresh token stored in cookie
      deleteCookie('refresh_token');
      location.replace('/login');
    }

    function fail() {
      alert('로그아웃 실패했습니다.');
    }

    httpRequest('DELETE', '/api/refresh-token', null, success, fail);
  });
}

// Function to get cookie
function getCookie(key) {
  var result = null;
  var cookie = document.cookie.split(';');
  cookie.some(function (item) {
    item = item.replace(' ', '');

    var dic = item.split('=');

    if (key === dic[0]) {
      result = dic[1];
      return true;
    }
  });

  return result;
}

// Function to delete cookies
function deleteCookie(name) {
  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

// Function that sends an HTTP request
function httpRequest(method, url, body, success, fail) {
  fetch(url, {
    method: method,
    headers: { // Get access token value from local storage and add it to header
      Authorization: 'Bearer ' + localStorage.getItem('access_token'),
      'Content-Type': 'application/json',
    },
    body: body,
  }).then(response => {
    if (response.status === 200 || response.status === 201) {
      return success();
    }
    const refresh_token = getCookie('refresh_token');
    if (response.status === 401 && refresh_token) {
      fetch('/api/token', {
        method: 'POST',
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('access_token'),
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          refreshToken: getCookie('refresh_token'),
        }),
      })
      .then(res => {
        if (res.ok) {
          return res.json();
        }
      })
      .then(result => { // If re-issuance is successful, replace the local storage value with a new access token
        localStorage.setItem('access_token', result.accessToken);
        httpRequest(method, url, body, success, fail);
      })
      .catch(error => fail());
    } else {
      return fail();
    }
  });
}
