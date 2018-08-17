class adminList {
  constructor() {
    this.init();
    this.inputEmail = document.querySelector('#input-email');
  }

  init() {
    document.querySelector('.add-administrator').addEventListener('click', e => {
      this.addStaff(e);
    });

    document.querySelector('tbody').addEventListener('click', e => {
      if (e.target.classList.contains('delete-btn')) {
        this.deleteStaff(e.target);
        return;
      }
    })
  }

  addStaff(e) {
    e.preventDefault();
    const data = {
      'email': this.inputEmail.value
    };

    fetch('/administrator/add', {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if (res.status === 200) {
        return res.json();
      }
    }).then(json => {
      const staff = `
        <tr data-item="${json.id}">
            <td>${json.id}</td>
            <td class="nickname">${json.nickname}</td>
            <td class="email">${json.email}</td>
                <td>
                    <button class="delete-btn">삭제</button>
                </td>
            </tr>`;

      document.querySelector('tbody').insertAdjacentHTML('beforeend', staff);
    });
  }

  deleteStaff(target) {
    const isDelete = confirm('관리자 권한을 삭제하시겠습니까?');
    if (!isDelete) {
      return
    }

    const staff = target.closest('tr');
    const id = staff.getAttribute('data-item');
    fetch(`/administrator/delete/${id}`, {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      if (res.status === 200) {
        staff.remove();
      }
    })
  };
}


export default adminList;

