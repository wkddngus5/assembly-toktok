class editPassword {
  constructor() {
    this.submitBtn = document.querySelector('input.submit-btn');
    this.inputPassword = document.querySelector('#password');
    this.inputPasswordConfirm = document.querySelector('#password-confirm');
    this.snackBar = document.querySelector('#demo-snackbar-example');
    this.init();
  }

  init() {
    this.submitBtn.addEventListener('click', e => {
      e.preventDefault();
      this.postPassword();
    });
  }

  postPassword() {
    const password = this.inputPassword.value;
    const passwordConfirm = this.inputPasswordConfirm.value;
    const token = location.href.split('token=')[1];

    const validCheck = this.checkValidPassword(password, passwordConfirm);
    if(validCheck !== 'ok') {
      this.showSnackBar(validCheck);
      return;
    }

    const data = {
      'token': token,
      'password': password
    };

    fetch('/users/password', {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status !== 200) {
        this.showSnackBar('비밀번호 변경에 실패했습니다.');
        return;
      }
      this.showSnackBar('비밀번호 변경이 완료되었습니다.');
      setTimeout(() => {
        window.location.href = '/login';
      }, 2000);
    });
  }

  checkValidPassword(password, passwordConfirm) {
    if(password.length < 5) {
      return '부적절한 비밀번호입니다.';
    } else if(password != passwordConfirm) {
      return '비밀번호와 비밀번호 확인이 일치하지 않습니다.'
    }
    return 'ok';
  }

  showSnackBar(message) {
    const handler = (event) => {
      showSnackbarButton.style.backgroundColor = '';
    };

    const data = {
      message: message,
      timeout: 2000,
      actionHandler: handler,
      actionText: ' '
    };

    this.snackBar.MaterialSnackbar.showSnackbar(data);
  }
}

new editPassword();