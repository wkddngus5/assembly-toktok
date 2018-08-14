class projectForm {
  constructor() {
    this.pages = document.querySelectorAll('.page');
    this.main = document.querySelector('main');
    this.projectImageInput = document.querySelector('input.project-img');
    this.projectImagePreview = document.querySelector('div.image-preview');
    this.completeBtn = document.querySelector('button.complete');
    this.init();
  }

  init() {
    CKEDITOR.replace( 'editor1', {
        toolbar: [
          { name: 'clipboard', items: [ 'Undo', 'Redo' ] },
          { name: 'styles', items: [ 'Format', 'Font', 'FontSize' ] },
          { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
          { name: 'align', items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
          '/',
          { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'RemoveFormat', 'CopyFormatting' ] },
          { name: 'links', items: [ 'Link', 'Unlink' ] },
          { name: 'insert', items: [ 'Image', 'Table', 'Video' ] },
          { name: 'tools', items: [ 'Maximize' ] },
          { name: 'editing', items: [ 'Scayt' ] }
        ],

      extraAllowedContent: 'h3{clear};h2{line-height};h2 h3{margin-left,margin-top}',

      // Adding drag and drop image upload.
      extraPlugins: 'print,format,font,colorbutton,justify,uploadimage',
      uploadUrl: '/api/aws/s3/upload',

      filebrowserBrowseUrl: '/api/aws/s3/list',
      filebrowserImageBrowseUrl: '/api/aws/s3/list',
      filebrowserUploadUrl: '/api/aws/s3/upload',
      filebrowserImageUploadUrl: '/api/aws/s3/upload',

      height: 560,

      removeDialogTabs: 'image:advanced;link:advanced'
    });

    document.querySelectorAll('button.next').forEach((button, i) => {
      button.addEventListener('click', e => {
        this.main.style.marginTop = this.pageHeight * i * (-1) + 100 + 'px';
        // this.moveNextPage(i);
      });
    });

    this.completeBtn.addEventListener('click', e => {
      e.preventDefault();
      const data = {
        'proposer': document.querySelector('#name').value,
        'proposer_email': document.querySelector('#email').value,
        'proposer_phone': document.querySelector('#phone').value,
        'proposer_description': document.querySelector('#introduce').value,
        'title': document.querySelector('#title').value,
        'summary': document.querySelector('#desc').value,
        'body': CKEDITOR.instances.editor1.getData(),
        'category': document.querySelector('ul.category-list .is-checked').innerText
      };

      console.log('data: ', data);

      fetch('/projects', {
        method: 'POST',
        credentials: 'same-origin',
        headers: new Headers({
          'accept': 'application/json',
          'content-type': 'application/json',
        }),
        body: JSON.stringify(data)
      }).then(res => {
        if(res.status === 200) {
          window.location = '/';
        }
        return res.json();
      }).then(json => {
        console.log(json);
      });
    });

    document.querySelector('ul.category-list').addEventListener('click', e => {
      if(e.target.tagName === 'LI') {
        this.toggleCategoryClick(e.target);
      }
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

    const formData = new FormData();
    formData.append('file', target.files[0]);
    const options = {
      method: 'POST',
      body: formData
    };

    fetch('/api/aws/s3/upload', options)
      .then(res => {
        console.log(res);
      });
  }

  toggleCategoryClick(target) {
    if(target.classList.contains('is-checked')) {
      target.classList.remove('is-checked');
    } else {
      const preChekced = document.querySelector('ul.category-list .is-checked');
      if(preChekced) {
        preChekced.classList.remove('is-checked');
      }
      target.classList.add('is-checked');
    }
  }

  resetPageHeight() {
    this.pageHeight = (window.innerHeight * 0.75 - 166);
    this.pages.forEach(page => {
      page.style.height = this.pageHeight + 'px';
    });
  }
}

export default projectForm;