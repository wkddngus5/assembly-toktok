class userForm {
  constructor() {
    this.snackBar = document.querySelector('#demo-snackbar-example');
    this.submitBtn = document.querySelector('input.submit-btn');
    this.inputEmail = document.querySelector('#email');
    this.inputPassword = document.querySelector('#password');
    this.inputPasswordConfirm = document.querySelector('#password-confirm');
    this.inputNickname = document.querySelector('#nickname');
    this.aggree = document.querySelector('#agree');

    this.regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    this.init();
  }

  init() {
    this.submitBtn.addEventListener('click', this.postUser.bind(this));
    document.querySelector('.agree-info.userAgreement').addEventListener('click', e => {
      window.open('/userAgreement');
    })

    document.querySelector('.agree-info.privacy').addEventListener('click', e => {
      window.open('/privacy');
    })
  }

  postUser(e) {
    e.preventDefault();
    if(!this.isValidForm()) {
      return;
    }

    const data = {
      'email': this.inputEmail.value,
      'password': this.inputPassword.value,
      'nickname': this.inputNickname.value
    };

    fetch('/users', {
      method: 'POST',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 200) {
        window.location = '/login';
      } else if(res.status === 500) {
        this.showSnackBar('이미 존재하는 계정입니다.');
      }
      return res.json();
    });
  }


  isValidForm() {
    if(!this.verifyEmail(this.inputEmail.value)) {
      this.showSnackBar('이메일 형식이 올바르지 않습니다.');
      return false;
    }

    if(this.inputPassword.value !== this.inputPasswordConfirm.value) {
      this.showSnackBar('패스워드를 올바르게 입력해주세요.');
      return false;
    }

    if(this.inputNickname.value.length < 3) {
      this.showSnackBar('닉네임은 3글자 이상으로 입력해주세요.');
      return false;
    }

    if(!this.aggree.checked) {
      this.showSnackBar('이용약관과 개인정보 취급방침에 동의해주세요.');
      return false;
    }

    return true;
  }

  verifyEmail(value) {
    return value.match(this.regExp) != null;
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

export default userForm;