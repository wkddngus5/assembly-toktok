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
      uploadUrl: '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files&responseType=json',

      // Configure your file manager integration. This example uses CKFinder 3 for PHP.
      filebrowserBrowseUrl: '/ckfinder/ckfinder.html',
      filebrowserImageBrowseUrl: '/ckfinder/ckfinder.html?type=Images',
      filebrowserUploadUrl: '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files',
      filebrowserImageUploadUrl: '/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images',

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
        'body': CKEDITOR.instances.editor1.getData()
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

  }

  toggleCategoryClick(target) {
    if(target.classList.contains('is-checked')) {
      target.classList.remove('is-checked');
    } else {
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