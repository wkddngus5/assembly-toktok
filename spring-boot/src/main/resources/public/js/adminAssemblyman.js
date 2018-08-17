class adminAssemblyman {
  constructor() {
    this.init();
    this.inputName = document.querySelector('#input-name');
    this.selectParty = document.querySelector('#select-party');
    this.selectCommittee = document.querySelector('#select-committee');
    this.inputEmail = document.querySelector('#input-email');
  }

  init() {
    document.querySelector('.add-assemblyman-btn').addEventListener('click', e => {
      this.postAssemblyman(e);
    });

    document.querySelector('tbody').addEventListener('click', e => {
      if(e.target.classList.contains('delete-btn')) {
        this.deleteAssemblyman(e.target);
        return;
      }
      
      if(e.target.classList.contains('update-btn')) {
        this.updateAssemblyman(e.target);
        return;
      }
    })
  }

  updateAssemblyman(target) {
    const assemblyman = target.closest('tr');
    const id = assemblyman.getAttribute('data-item');

    const data = {
      'name': assemblyman.querySelector('.input-name').value,
      'party': assemblyman.querySelector('.input-party').value,
      'committee_id': assemblyman.querySelector('.input-committee').value,
      'email': assemblyman.querySelector('.input-email').value
    };

    fetch(`/administrator/assemblyman/${id}`, {
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
  };


  deleteAssemblyman(target) {
    const isDelete = confirm('삭제하시겠습니까?');
    if(!isDelete) {
      return
    }

    const assemblyman = target.closest('tr');
    const id = assemblyman.getAttribute('data-item');
    fetch(`/administrator/assemblyman/${id}`, {
      method: 'DELETE',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      if(res.status === 200) {
        assemblyman.remove();
      }
    })
  };


  postAssemblyman(e) {
    e.preventDefault();
    const data = {
      'name': this.inputName.value,
      'party': this.selectParty.value,
      'committee': this.selectCommittee.value,
      'email': this.inputEmail.value
    };

    fetch('/administrator/assemblyman', {
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
      const newAssemblyman = `
        <tr data-item="${json.id}}">
            <td>${json.id}</td>
            <td class="name"><input class="input-name" value="${json.name}"></td>
            <td class="party"><input class="input-party" value="${json.party}"></td>
            <td class="committee"><input class="input-committee" value="${json.committee_id}"></td>
            <td class="email"><input class="input-email" value="${json.email}"></td>
            <td class="image"><img class="image" data-item="${json.image}"></td>
            <td>
              <button class="update-btn">수정</button>
              <button class="delete-btn">삭제</button>
            </td>
        </tr>`;
      document.querySelector('tbody').insertAdjacentHTML('beforeend', newAssemblyman);
    })
  }
}

export default adminAssemblyman;

