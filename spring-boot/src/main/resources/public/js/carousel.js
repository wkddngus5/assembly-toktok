class carousel {
  constructor() {
    this.nowIndex = 0;
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
      let nextPageOrder = this.nowIndex + 2;
      if(this.indexList.length === this.nowIndex + 1) {
        nextPageOrder = 1;
      }
      this.moveCarousel(nextPageOrder);
    }, 5000)
  }


  resizeCarousel() {
    const pageListLength = this.pageList.length * document.querySelector('.main-carousel').offsetWidth + 1;
    document.querySelector('ul.carousel-page-list').style.width = `${pageListLength}px`;
    this.pageList.forEach(page => {
      page.style.width = 100 / this.pageList.length + '%';
    });
  }


  moveCarousel(order) {
    const index = order - 1;
    document.querySelector('ul.carousel-page-list').style.marginLeft = `-${(index) * 100}%`;
    this.pageList[this.nowIndex].classList.remove('is-visible');
    this.indexList[this.nowIndex].classList.remove('is-checked');
    this.nowIndex = index;
    this.pageList[this.nowIndex].classList.add('is-visible');
    this.indexList[this.nowIndex].classList.add('is-checked');
  }
}

export default carousel;
