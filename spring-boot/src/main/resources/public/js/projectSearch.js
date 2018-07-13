class projectSearch {
  constructor() {
    this.inputSearch = document.querySelector('main .input-search');
    this.init();
  }

  init() {
    this.inputSearch.addEventListener('keydown', e => {
      if(e.keyCode === 13) {
        e.preventDefault();
        location.href = '/projects/search?keyword=' + e.target.value;
      }
    });
  }
}

new projectSearch();