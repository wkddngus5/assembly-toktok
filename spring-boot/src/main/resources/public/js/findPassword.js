class findPassword {
  constructor() {
    this.regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

    this.submitBtn = document.querySelector('.submit-btn');
    this.inputEmail = document.querySelector('input.input-email');
    this.snackBar = document.querySelector('#demo-snackbar-example');
    this.init();
  }

  init() {
    this.submitBtn.addEventListener('click', e => {
      e.preventDefault();
      this.postEmail();
    });
  }

  postEmail() {
    const email = this.inputEmail.value;
    if(!this.verifyEmail(email)) {
      this.showSnackBar('이메일 형식이 올바르지 않습니다.');
      return;
    }

    const data = {
      'email': email
    };

    this.showSnackBar('잠시 기다려주세요.');

    fetch('/users/password', {
      method: 'POST',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status !== 200) {
        this.showSnackBar('가입되지 않은 이메일입니다.');
        return;
      }
      this.showSnackBar('입력하신 이메일로 변경 메일을 보냈습니다.');
    });
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

  verifyEmail(value) {
    return value.match(this.regExp) != null;
  }
}

new findPassword();