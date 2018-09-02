class my {
  constructor() {
    this.snackBar = document.querySelector('#demo-snackbar-example');

    this.inputNickname = document.querySelector('#nickname');
    this.inputPassword = document.querySelector('#password');
    this.inputNewPassword = document.querySelector('#new-password');
    this.inputNewPasswordConfirm = document.querySelector('#new-password-confirm');

    this.changeNicknameBtn = document.querySelector('#change-nickname-btn');
    this.changePasswordBtn = document.querySelector('#change-password-btn');

    this.changeNicknameZone = document.querySelector('div.change-nickname-zone');
    this.changePasswordZone = document.querySelector('div.change-password-zone');

    this.profileImg = document.querySelector('div.mdl-card__title.mdl-card--expand');
    this.inputProfileImg = document.querySelector('input.profile-img');

    this.projectsNav = document.querySelector('nav.project-list-btns');
    this.projectListArray = document.querySelectorAll('ul.project-list');

    this.changeProfileImg = this.changeProfileImg.bind(this);
    this.changeProjects = this.changeProjects.bind(this);

    this.init();
  }

  init() {
    this.projectsInit();
    this.initProfileImg();
    document.querySelector('#update-user-btn').addEventListener('click', e => {
      this.updateUser();
    });

    this.changeNicknameBtn.addEventListener('click', e => {
      this.changePasswordZone.classList.remove('is-visible');
      this.changeNicknameZone.classList.add('is-visible');
    });

    this.changePasswordBtn.addEventListener('click', e => {
      this.changeNicknameZone.classList.remove('is-visible');
      this.changePasswordZone.classList.add('is-visible');
    });

    this.profileImg.addEventListener('click', e => {
      this.inputProfileImg.click();
    });
    this.inputProfileImg.addEventListener('change', this.changeProfileImg);
    this.projectsNav.addEventListener('click', this.changeProjects);

    document.querySelector('#participations-btn').classList.add('is-active');
    document.querySelector('ul.project-list.participations').classList.add('is-visible');
  }

  initProfileImg() {
    const imageZone = document.querySelector('.mdl-card__title.mdl-card--expand');
    if(imageZone.getAttribute('style').includes('null')) {
      imageZone.removeAttribute('style');
    }
  }

  updateUser() {
    const visible = document.querySelector('div.is-visible');
    this.updateImage();
    if(!visible) {
      return;
    }
    if(visible.classList.contains('change-nickname-zone')) {
      this.updateNickname();
    } else if(visible.classList.contains('change-password-zone')) {
      this.updatePassword();
    }
  }

  updateImage() {
    const image = document.querySelector('input.profile-img').getAttribute('data-item');
    if(!image) {
      return;
    }

    const data = {
      'image': image
    };

    fetch('/users/image', {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 200) {
        alert('프로필 이미지가 성공적으로 변경되었습니다.');
        window.location = '/my';
      }
    });
  }

  updateNickname() {
    const data = {
      'nickname': this.inputNickname.value
    };

    if(data.nickname.length < 2 || data.nickname.length > 10) {
      this.showSnackBar('닉네임은 2글자 이상 10글자 미만으로 설정해주세요.');
      return;
    }

    fetch('/users/nickname', {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 200) {
        alert('닉네임이 성공적으로 변경되었습니다.');
        window.location = '/my';
      }
    });
  }

  updatePassword() {
    const REG_PWD = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
    const password = this.inputNewPasswordConfirm.value;
    if(!REG_PWD.test(password)) {
      this.showSnackBar('비밀번호는 영문, 숫자를 혼합하여 6~20자리 이내로 설정해주세요.');
      return;
    }

    if(this.inputNewPassword.value !== this.inputNewPasswordConfirm.value) {
      this.showSnackBar('새 비밀번호를 다시 확인해주세요.');
      return;
    }

    const data = {
      'password': this.inputNewPassword.value
    };

    const image = document.querySelector('input.profile-img').getAttribute('data-item');
    if(image) {
      data.image = image
    }

    fetch('/users/password', {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      console.log(res);
    });
  }


  projectsInit() {
    document.querySelectorAll('li.project').forEach((li, index) => {
      const status = li.querySelector('div.status');
      const projectImg = li.querySelector('div.project-img');
      if (projectImg.getAttribute('style').includes('null')) {
        projectImg.removeAttribute('style');
      }
      const nowStatus = status.getAttribute('data-item');
      switch (nowStatus) {
        case 'running':
          status.innerText = '입법활동';
          status.classList.add(nowStatus);
          break;
        case 'fail':
          status.innerText = '매칭실패';
          status.classList.add(nowStatus);
          break;
      }
      const goalCount = li.querySelector('.percentage-number').getAttribute('data-item');
      const count = li.querySelector('.count').getAttribute('data-item');
      const percentage = ((count / goalCount * 100).toFixed(1));

      li.querySelector('.percentage-number').innerText = percentage + '%';
      li.querySelector('.percentage').style.width = percentage > 100 ? '100%' : percentage + '%';
    });
  }

  changeProjects(e) {
    if(e.target.tagName !== 'SPAN') {
      return;
    }

    const activeBtn = this.projectsNav.querySelector('.is-active');
    if( activeBtn !== null) {
      activeBtn.classList.remove('is-active');
      document.querySelector('div.project-list-zone ul.is-visible').classList.remove('is-visible');
    }

    const clickedBtn = e.target.parentNode;
    clickedBtn.classList.add('is-active');
    document.querySelector('ul.project-list.' + clickedBtn.getAttribute('data-item')).classList.add('is-visible');

  }

  changeProfileImg() {
    let file = this.inputProfileImg.files[0];
    let reader = new FileReader();

    reader.addEventListener('load', () => {
      console.log("CHANGE");
      this.profileImg.style.backgroundImage = 'url(' + reader.result + ')';
    }, false);


    if(file) {
      reader.readAsDataURL(file);
      this.profileImg.classList.add('is-active');
    }

    const formData = new FormData();
    formData.append('file', this.inputProfileImg.files[0]);
    const options = {
      method: 'POST',
      body: formData
    };

    fetch('/api/aws/s3/upload', options)
      .then(res => {
        if(res.status === 200) {
          return res.json();
        }
      }).then(json => {
      document.querySelector('input.profile-img').setAttribute('data-item', json[0]);
    })
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

export default my;