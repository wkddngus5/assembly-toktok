class adminCommittees {
  constructor() {
    this.init();
    this.inputName = document.querySelector('.input-name');
  }

  init() {
    document.querySelector('.add-committee-btn').addEventListener('click', e => {
      this.postCommittee();
    });

    document.querySelector('tbody').addEventListener('click', e => {
      if(e.target.classList.contains('delete')) {
        this.deleteCommittee(e.target);
        return;
      }
      
      if(e.target.classList.contains('update')) {
        this.updateCommittee(e.target);
        return;
      }
    })
  }

  updateCommittee(target) {
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

  deleteCommittee(target) {
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

  postCommittee() {
    const data = {
      'name': this.inputName.value
    };

    fetch('/administrator/committees', {
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
}


export default adminCommittees;
