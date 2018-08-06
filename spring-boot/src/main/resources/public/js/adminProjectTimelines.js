class adminProjectTimelines {
  constructor() {
    this.timelines = document.querySelector('table.timelines');
    this.inputDate = document.querySelector('#input-date');
    this.inputBody = document.querySelector('#input-timeline-contents');
    this.subject = document.querySelector('#subject');
    this.init();
  }

  init() {
    this.timelines.addEventListener('click', e => {
      const target = e.target;
      if(target.classList.contains('remove-timeline-btn')) {
        this.deleteTimeline(target);
      }
    });
    document.querySelector('.add-timeline-btn').addEventListener('click', e => {
      this.addTimeline();
    })
  }

  addTimeline() {
    const projectId = document.querySelector('h2').getAttribute('data-item');
    const data = {
      'body': this.inputBody.value,
      'project_id': projectId,
      'timeline_date': this.inputDate.value,
      'subject': this.subject.value
    };

    fetch(`/administrator/projects/${projectId}/timelines/`, {
      method: 'POST',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      return res.json();
    }).then(json => {
      const timeline = `
        <tr id="${json.id}">
            <td>${json.created_at}</td>
            <td>${json.body}</td>
            <td>${json.subject}</td>
            <td>
                <button class="update-timeline-btn">수정</button>
                <button class="remove-timeline-btn">삭제</button>
            </td>
        </tr>
      `;
      document.querySelector('tbody').insertAdjacentHTML('beforeend', timeline);
      console.log(json);
    });
  }


  deleteTimeline(target) {
    const tr = target.closest('tr');
    const projectId = document.querySelector('h2').getAttribute('data-item');
    fetch(`/administrator/projects/${projectId}/timelines/${tr.id}`, {
      method: 'DELETE',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      console.log(res);
      if(res.status !== 200) {
        return;
      } else {
        tr.remove();
      }
    })
  }
}

export default adminProjectTimelines;
