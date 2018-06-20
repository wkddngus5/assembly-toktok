class header {
  constructor() {
    this.searchForm = document.querySelector('form#search-zone');
    this.inputSearch = document.querySelector('#input-search');
    this.init();
  }

  init() {
    this.inputSearch.addEventListener('focus', () => {
      this.searchForm.classList.add('focus');
      this.inputSearch.classList.add('focus');
    });

    this.inputSearch.addEventListener('focusout', () => {
      this.searchForm.classList.remove('focus');
      this.inputSearch.classList.remove('focus');
    });

    // this.searchForm.addEventListener('click', () => {
    // });
  }
}

new header();

// export default header;