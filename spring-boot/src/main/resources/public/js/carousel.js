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
  }

  resizeCarousel() {
    const pageListLength = this.pageList.length * document.querySelector('.main-carousel').offsetWidth + 1;
    document.querySelector('ul.carousel-page-list').style.width = `${pageListLength}px`;
    this.pageList.forEach(page => {
      page.style.width = 100 / this.pageList.length + '%';
    });
  }

  moveCarousel(index) {
    // console.log(index);
    // for(let i = 0 ; i < index ; i++) {
    //   this.pageList[i].style.marginLeft = `-${(index - i) * 100}%`;
    // }
    document.querySelector('ul.carousel-page-list').style.marginLeft = `-${(index - 1) * 100}%`
    this.pageList[this.nowIndex - 1].classList.remove('is-visible');
    this.indexList[this.nowIndex - 1].classList.remove('is-checked');
    this.nowIndex = index;
    this.pageList[this.nowIndex - 1].classList.add('is-visible');
    this.indexList[this.nowIndex - 1].classList.add('is-checked');
  }
}

export default carousel;

