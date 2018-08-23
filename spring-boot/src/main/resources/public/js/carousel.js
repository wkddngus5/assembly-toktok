class carousel {
  constructor() {
    this.nowIndex = 1;
    this.pageList = document.querySelectorAll('li.carousel-page');
    this.indexList = document.querySelectorAll('li.carousel-index');
    this.init();
  }

  init() {
    this.resizeCarousel();
    window.onresize = () => {
      this.resizeCarousel();
    };

    document.querySelector('.carousel-index-list').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.moveCarousel(e.target.getAttribute('data-item'));
      }
    });

    setInterval(() => {
      if(this.indexList.length === this.nowIndex) {
        this.nowIndex = 0;
      }

      this.moveCarousel(++this.nowIndex);
    }, 5000)
  }

  resizeCarousel() {
    const pageListLength = this.pageList.length * document.querySelector('.main-carousel').offsetWidth + 1;
    document.querySelector('ul.carousel-page-list').style.width = `${pageListLength}px`;
    this.pageList.forEach(page => {
      page.style.width = 100 / this.pageList.length + '%';
    });
  }

  moveCarousel(index) {
    document.querySelector('ul.carousel-page-list').style.marginLeft = `-${(index - 1) * 100}%`
    this.pageList[index - 2].classList.remove('is-visible');
    this.indexList[index - 2].classList.remove('is-checked');
    this.nowIndex = index;
    this.pageList[index - 1].classList.add('is-visible');
    this.indexList[index - 1].classList.add('is-checked');
  }
}

export default carousel;
