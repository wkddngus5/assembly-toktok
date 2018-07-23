class adminProjects {
  constructor() {
    this.table = document.querySelector('table.project-list');
    this.init();
  }

  init() {
    this.table.addEventListener('click', this.removeProject);
    this.countProjects();

  }

  countProjects() {
    const count = this.table.querySelectorAll('tr').length - 1;
    document.querySelector('h6.projects-count').innerText = `제안 수: ${count}개`;
  }


  removeProject(e) {
    if(!e.target.classList.contains('remove')) {
      return;
    }

    const canRemove = confirm('해당 제안을 삭제하시겠습니까?' +
      '\n삭제한 제안은 다시 볼 수 없습니다.');

    if(!canRemove) {
      return;
    }

    // fetch('/administrator/projects/id', {
    //   method: 'DELETE',
    //   credentials: 'same-origin',
    //   headers: new Headers({
    //     'accept': 'application/json',
    //     'content-type': 'application/json',
    //   })
    // }).then(res => {
    //   if(res.status === 200) {
        e.target.closest('tr').remove();
    //   }
    // })
  }
}

new adminProjects();
