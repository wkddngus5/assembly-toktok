class mainProjects {
  constructor() {
    this.init();
  }

  init() {
    document.querySelectorAll('li.project').forEach((li, index) => {
      const goalCount = li.querySelector('.percentage-number').getAttribute('data-item');
      const count = li.querySelector('.count').getAttribute('data-item');
      const percentage = ((count / goalCount * 100).toFixed(1));

      if(index === 3 && window.innerWidth > 1130) {
        li.classList.add('long');
      }

      li.querySelector('.percentage-number').innerText = percentage + '%';
      li.querySelector('.percentage').style.width = percentage > 100 ? '100%' : percentage + '%';
    });

    window.onresize = () => {
      const long = document.querySelector('li.long');
      if(window.innerWidth < 1130 && long) {
        long.classList.remove('long');
      } else if(window.innerWidth > 1130) {
        document.querySelectorAll('li.project')[3].classList.add('long');
      }
    }
  }
}

new mainProjects();