class projectSearch {
  constructor() {
    this.inputSearch = document.querySelector('main .input-search');
    this.init();
  }

  init() {
    this.projectsInit();
    this.inputSearch.addEventListener('keydown', e => {
      if(e.keyCode === 13) {
        e.preventDefault();
        location.href = '/projects/search?keyword=' + e.target.value;
      }
    });
  }

  projectsInit() {
    document.querySelectorAll('li.project').forEach((li, index) => {
      const status = li.querySelector('div.status');
      const projectImg = li.querySelector('div.project-img');
      if(projectImg.getAttribute('style').includes('null')) {
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
}

export default projectSearch;