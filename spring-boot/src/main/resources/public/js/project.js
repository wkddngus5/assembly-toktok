class project {
  constructor() {
    this.infoList = document.querySelectorAll('nav.info-subject button');
    this.nowActiveInfo = document.querySelector('nav.info-subject button.active');
    this.nowVisibleInfo = document.querySelector('div.info .is-visible');
    this.dim = document.querySelector('div.dim');
    this.infoModal = document.querySelector('div.info-modal');
    this.statusZone = document.querySelector('ul.status-zone');
    this.timelineList = document.querySelectorAll('li.each-timeline');
    this.showingGuide = null;

    this.toggleGuide = this.toggleGuide.bind(this);
    this.closeGuide = this.closeGuide.bind(this);
    this.init();
  };

  init() {
    this.percentageSet();
    this.statusSet();
    document.querySelector('.join-btn').addEventListener('click', e => {
      this.join(e.target);
    });

    document.querySelector('nav.info-subject').addEventListener('click', e => {
      if(e.target.tagName === 'BUTTON'){
        this.switchInfo(e.target);
      }
    });

    this.hideLastTimelineOuter();
    document.querySelector('ul.status-zone').addEventListener('click', this.toggleGuide);
    document.addEventListener('click', this.closeGuide);
  };

  closeGuide(e) {
    if(!this.showingGuide || e.target.tagName === 'LI') {
      return;
    }

    e.preventDefault();
    this.showingGuide.classList.remove('is-visible');
    this.showingGuide = null;
  }

  toggleGuide(e) {
    if(e.target.classList.contains('active')) {
      return;
    }

    e.target.querySelector('div.guide').classList.add('is-visible');
    if(this.showingGuide) {
      this.showingGuide.classList.remove('is-visible');
    }

    this.showingGuide = document.querySelector('div.guide.is-visible');
  }

  hideLastTimelineOuter() {
    if(this.timelineList.length < 1) {
      return;
    }
    this.timelineList[this.timelineList.length - 1].querySelector('.outer-border').classList.add('white');
  }

  statusSet() {
    let nowStatus = this.statusZone.getAttribute('data-item');
    switch (nowStatus) {
      case 'running':
        this.statusZone.querySelector('.lawmaking').classList.add('active');
        break;
      case 'fail':
        let step = this.statusZone.querySelector('.matching');
        step.classList.add('active');
        step.innerText = '매칭실패';
        break;
      default:
        this.statusZone.querySelector('.participation').classList.add('active');
    }
  }

  modalOn() {
    this.dim.classList.add('is-visible');
    this.infoModal.classList.add('is-visible');
  }

  modalOff() {
    this.dim.classList.remove('is-visible');
    this.infoModal.classList.remove('is-visible');
  }

  percentageSet() {
    const count = document.querySelector('p.count').innerText;
    const goalCount = document.querySelector('.percentage-number').getAttribute('data-item');

    const percentage = (parseInt(count) / parseInt(goalCount) * 100).toFixed(1);

    document.querySelector('.percentage-zone .percentage').style.width =
      (percentage > 100.0 ? 100.0 : percentage) + '%';
    document.querySelector('.percentage-number').innerText = percentage + '%';
  }

  switchInfo(target) {
    this.nowActiveInfo.classList.remove('active');
    this.nowActiveInfo = target;
    this.nowActiveInfo.classList.add('active');

    let clickedData = target.getAttribute('data-item');
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

export default project;
