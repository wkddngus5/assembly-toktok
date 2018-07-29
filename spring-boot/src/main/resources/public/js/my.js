class my {
  constructor() {
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
    this.changeNicknameBtn.addEventListener('click', e => {
      this.changePasswordZone.classList.remove('is-visible');
      this.changeNicknameZone.classList.add('is-visible');
    });

    this.changePasswordBtn.addEventListener('click', e => {
      this.changeNicknameZone.classList.remove('is-visible');
      this.changePasswordZone.classList.add('is-visible');
    });

    this.profileImg.addEventListener('click', this.changeProfileImg);

    this.projectsNav.addEventListener('click', this.changeProjects);
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

    this.inputProfileImg.click();

    reader.addEventListener('load', () => {
      this.profileImg.style.backgroundImage = 'url(' + reader.result + ')';
    }, false);

    if(file) {
      reader.readAsDataURL(file);
    }
  }
}

export default my;