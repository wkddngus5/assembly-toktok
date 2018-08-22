class adminProjectForm {
  constructor() {
    this.inputTitle = document.querySelector('#title');
    this.inputName = document.querySelector('#name');
    this.inputEmail = document.querySelector('#email');
    this.inputPhone = document.querySelector('#phone');
    this.selectCategory = document.querySelector('select.category');
    this.inputSummary = document.querySelector('#summary');
    this.inputDescription = document.querySelector('#description');
    this.image = document.querySelector('#image');

    this.submitBtn = document.querySelector('#submit-btn');

    this.init();
  }


  init() {
    CKEDITOR.replace( 'editor', {
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

    this.initCategory();
    this.submitBtn.addEventListener('click', e => {
      this.updateProject();
    })
  }

  initCategory() {
    const nowCategory = document.querySelector('.category').getAttribute('data-item');
    if(nowCategory === '') {
      return;
    }

    const categoryList = document.querySelectorAll('select.category option');
    for(let i = 0 ; i < categoryList.length ; i++) {
      if(categoryList[i].value === nowCategory) {
        categoryList[i].selected = 'selected';
      }
    }
  }

  updateProject() {
    const id = document.querySelector('main').getAttribute('data-item');

    let data = {
      'title': this.inputTitle.value,
      'proposer': this.inputName.value,
      'proposer_email': this.inputEmail.value,
      'proposer_phone': this.inputPhone.value,
      'category': this.selectCategory.value,
      'body': CKEDITOR.instances.editor.getData(),
      'summary': this.inputSummary.value,
      'proposer_description': this.inputDescription.value,
      'image': this.image.getAttribute('data-item')
    };
    console.log(id, data);


    fetch(`/administrator/projects/${id}`, {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if (res.status === 200) {
        alert('변경 완료');
        location.href = '/administrator/projects';
      }
    });
  }
}

export default adminProjectForm;
