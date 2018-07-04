class projectForm {
  constructor() {
    this.pages = document.querySelectorAll('.page');
    this.main = document.querySelector('main');
    this.projectImageInput = document.querySelector('input.project-img');
    this.projectImagePreview = document.querySelector('div.image-preview');
    this.init();
  }

  init() {
    document.querySelectorAll('button.next').forEach((button, i) => {
      console.log(button, i);
      button.addEventListener('click', e => {
        console.log(this.pageHeight);
        this.main.style.marginTop = this.pageHeight * i * (-1) + 100 + 'px';
        this.moveNextPage(i);
      });
    });

    document.querySelector('ul.category-list').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.toggleCategoryClick(e.target);
      }
    });

    const toolbarOptions = ['bold', 'italic', 'underline', 'strike', 'image', 'video'];

    const editor = new Quill('#editor', {
      modules: {     toolbar: toolbarOptions },
      theme: 'snow',
    });

    this.projectImagePreview.addEventListener('click', e => {
      this.projectImageInput.click();
    });

    this.projectImageInput.addEventListener('change', e => {
      this.changeProjectImage(e.target);
    });
  }

  changeProjectImage(target) {
    let file = target.files[0];
    let reader = new FileReader();

    reader.addEventListener('load', () => {
      this.projectImagePreview.innerHTML = '';
      this.projectImagePreview.style.backgroundImage = 'url(' + reader.result + ')';
    }, false);

    if(file) {
      reader.readAsDataURL(file);
    }

  }

  toggleCategoryClick(target) {
    if(target.classList.contains('is-checked')) {
      target.classList.remove('is-checked');
    } else {
      target.classList.add('is-checked');
    }
  }

  moveNextPage(i) {
    console.log(i);
  }

  resetPageHeight() {
    this.pageHeight = (window.innerHeight * 0.75 - 166);
    this.pages.forEach(page => {
      page.style.height = this.pageHeight + 'px';
    });
  }

}

new projectForm();