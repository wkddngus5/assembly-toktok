class mainProjects {
  constructor() {
    this.init();
  }

  init() {
    this.projectsInit();

    window.addEventListener("optimizedResize", () => {
      const long = document.querySelector('li.long');
      if (window.innerWidth < 1130 && long) {
        long.classList.remove('long');
      } else if (window.innerWidth > 1130) {
        const fourthProject = document.querySelectorAll('li.project')[3];
        if (fourthProject.closest('#imminent')) {
          fourthProject.classList.add('long');
        }
      }
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
        case 'matching':
          status.innerText = '매칭중';
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

      if (index === 3 && window.innerWidth > 1130 && li.closest('#imminent')) {
        li.classList.add('long');
      }

      li.querySelector('.percentage-number').innerText = percentage + '%';
      li.querySelector('.percentage').style.width = percentage > 100 ? '100%' : percentage + '%';
    });
  }
}

export default mainProjects;