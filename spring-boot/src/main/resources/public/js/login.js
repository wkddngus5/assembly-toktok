class login {
  constructor() {
    this.inputEmail = document.querySelector('input.input-email');
    this.inputPassword = document.querySelector('input.input-password');

    this.regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    this.init();
  }

  init() {
    document.querySelector('input.submit-btn').addEventListener('click', e => {
      e.preventDefault();
      this.login();
    });

    console.log(this.inputEmail);
    this.inputEmail.addEventListener('webkitAnimationEnd', this.removeBlink);
    this.inputEmail.addEventListener('animationend', this.removeBlink);
  }
  
  removeBlink(e) {
    console.log(e);
    this.classList.remove('blink')
  }

  login() {
    const data = {
      'email': this.inputEmail.value,
      'password': this.inputPassword.value
    };

    if(data.email.match(this.regExp) === null) {
      this.inputEmail.classList.add('blink');
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
      console.log(json);
    });
  }
}

new login();