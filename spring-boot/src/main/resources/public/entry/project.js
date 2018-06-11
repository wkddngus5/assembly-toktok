class project {
  constructor() {
    this.infoList = document.querySelectorAll('nav.info-subject button');
    this.nowActiveInfo = document.querySelector('nav.info-subject button.active');
    this.nowVisibleInfo = document.querySelector('div.info .is-visible');
    this.init();
  };

  init() {
    document.querySelector('.join-btn').addEventListener('click', e => {
      this.join(e.target);
    });

    document.querySelector('nav.info-subject').addEventListener('click', e => {
      if(e.target.tagName === 'BUTTON'){
        this.switchInfo(e.target);
      }
    });
  };

  switchInfo(target) {
    this.nowActiveInfo.classList.remove('active');
    this.nowActiveInfo = target;
    this.nowActiveInfo.classList.add('active');

    let clickedData = target.getAttribute('data-item');
    console.log(clickedData, this.nowVisibleInfo);
    this.nowVisibleInfo.classList.remove('is-visible');
    this.nowVisibleInfo = document.querySelector(`div.info .info-contents.${clickedData}`);
    this.nowVisibleInfo.classList.add('is-visible');
  }

  join(button) {
    let text = button.innerText;
    if(text === '참여하기') {
      button.innerText = '참여취소';
      button.style.backgroundColor = '#0000FF';
    } else {
      button.innerText = '참여하기';
      button.style.backgroundColor = '#fbef03';
    }
  }
}

(function () {
  new project();
})();
