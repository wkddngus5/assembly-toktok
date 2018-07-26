class login {
  constructor() {
    this.inputEmail = document.querySelector('input.input-email');
    this.inputPassword = document.querySelector('input.input-password');
    this.snackBar = document.querySelector('#demo-snackbar-example');

    this.regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    this.init();
  }

  init() {
    document.querySelector('input.submit-btn').addEventListener('click', e => {
      e.preventDefault();
      this.login();
    });

    this.inputEmail.addEventListener('webkitAnimationEnd', this.removeBlink);
    this.inputEmail.addEventListener('animationend', this.removeBlink);
  }
  
  removeBlink(e) {
    this.classList.remove('blink')
  }

  login() {
    const data = {
      'email': this.inputEmail.value,
      'password': this.inputPassword.value
    };

    if(data.email.match(this.regExp) === null) {
      this.inputEmail.classList.add('blink');
      this.showSnackBar("이메일 형식이 올바르지 않습니다.")
      return;
    }

    fetch('/users/auth/email', {
      method: 'POST',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 200) {
        window.location = '/';
      }
      return res.json();
    }).then(json => {
      this.showSnackBar('잘못된 회원정보입니다.');
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
}

new login();