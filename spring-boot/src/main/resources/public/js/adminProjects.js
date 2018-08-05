class adminProjects {
  constructor() {
    this.table = document.querySelector('table.project-list');
    this.projectList = document.querySelectorAll('tr.project');
    this.filterStatus = document.querySelector('select.filter-status');
    this.init();
  }

  init() {
    this.table.addEventListener('click', this.removeProject);
    this.filterStatus.addEventListener('change', e => {
      setTimeout(this.countProjects.bind(this), 2000);
      if(e.target.value === '전체') {
        this.projectList.forEach(project => {
          project.classList.remove('hide');
        });
        return;
      }

      this.projectList.forEach(project => {
        if(project.querySelector('.status').innerText === e.target.value) {
          project.classList.remove('hide');
        } else {
          project.classList.add('hide');
        }
      });
    });
    this.countProjects();
  }

  countProjects() {
    const count = this.projectList.length - document.querySelectorAll('tr.hide').length;
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

    fetch(`/administrator/projects/${e.target.getAttribute('data-item')}`, {
      method: 'DELETE',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      if(res.status === 200) {
        e.target.closest('tr').remove();
      }
    })
  }
}

export default adminProjects;
