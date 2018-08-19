class adminSlides {
  constructor() {
    this.inputOrder = document.querySelector('#input-order');
    this.inputImage = document.querySelector('#input-image');
    this.inputUrl = document.querySelector('#input-url');

    this.init();
  }

  init() {
    document.querySelector('.add-slide-btn').addEventListener('click', e => {
      this.postSlide(e);
    });

    document.querySelector('tbody').addEventListener('click', e => {
      if(e.target.classList.contains('delete-btn')) {
        this.deleteSlide(e.target);
        return;
      }
      
      if(e.target.classList.contains('update-btn')) {
        this.updateSlide(e.target);
        return;
      }
    });

    this.inputImage.addEventListener('change', e => {
      this.changeProjectImage(e.target);
    });
  }

  updateSlide(target) {
    const committee = target.closest('tr');
    const id = committee.getAttribute('data-item');

    const name = committee.querySelector('.name input').value;
    const data = {
      'name': name
    };

    fetch(`/administrator/committees/${id}`, {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 200) {
        alert('수정되었습니다.');
        location.reload();
      }
    })
  }

  deleteSlide(target) {
    const isDelete = confirm('삭제하시겠습니까?');
    if(!isDelete) {
      return
    };

    const committee = target.closest('tr');
    const id = committee.getAttribute('data-item');
    fetch(`/administrator/committees/${id}`, {
      method: 'DELETE',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      if(res.status === 200) {
        committee.remove();
      }
    })
  };

  postSlide(e) {
    e.preventDefault();
    const data = {
      'order': this.inputOrder.value,
      'url': this.inputUrl.value,
      'image': this.inputImage.getAttribute('data-item')
    };


    console.log(data);
    fetch('/administrator/slides', {
      method: 'POST',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 201) {
        return res.json();
      }
    }).then(json => {
      const newCommittee = `
        <tr id="committee-${json.id}" data-item="${json.id}">
            <td>${json.id}</td>
            <td><input value="${json.name}"></td>
            <td>
                <button class="update">수정</button>
                <button class="delete">삭제</button>
            </td>
        </tr>`;
      document.querySelector('tbody').insertAdjacentHTML('beforeend', newCommittee)
    })
  }

  changeProjectImage(target) {
    let file = target.files[0];
    let reader = new FileReader();

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
        if(res.status === 200) {
          return res.json();
        }
      }).then(json => {
        document.querySelector('#input-image').setAttribute('data-item', json[0]);
    })
  }
}

export default adminSlides;
