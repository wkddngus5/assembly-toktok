class adminProject {
  constructor() {
    this.committeeZone = document.querySelector('div.committee-zone');
    this.status = document.querySelector('#status');
    this.matchingPeriod = document.querySelector('#matching-period');
    this.matchingPeriodEnd = document.querySelector('#matching-period-end');
    this.goalCount = document.querySelector('#goal-count');
    this.platformUri = document.querySelector('#platform-uri');

    this.notice1 = document.querySelector('#notice1');
    this.notice2 = document.querySelector('#notice2');
    this.notice3 = document.querySelector('#notice3');

    this.inputAddAssemblyman = document.querySelector('input.add-assemblyman');
    this.inputAddCommittee = document.querySelector('#input-add-committee');
    this.init();
  }

  init() {
    this.initCommittees();
    this.committeeZone.addEventListener('click', e => {
      const target = e.target;
      if(target.classList.contains('remove-committee-btn')) {
        this.removeCommittee(target);
        return;
      }

      if(target.classList.contains('remove-assemblyman-btn')) {
        this.removeCongressmen(target);
        return;
      }

      if(target.classList.contains('add-assemblyman-btn')) {
        e.preventDefault();
        this.addAssemblyman(e.target);
      }
    });

    document.querySelector('button.submit').addEventListener('click', e => {
      e.preventDefault();
      this.updateProject();
    });

    document.querySelector('#add-committee-btn').addEventListener('click', e => {
      e.preventDefault();
      this.addCommittee();
    });
  }

  initCommittees() {
    const committeeData = document.querySelector('#admin-committees').getAttribute('data-item');
    if(committeeData === '') {
      return;
    }
    const committeessJson = JSON.parse(document.querySelector('#admin-committees').getAttribute('data-item'));
    const committeeList = committeessJson.committeeList;
    document.querySelector('#committee-date').value = committeessJson.date;
    for(let i = 0 ; i < committeeList.length ; i++) {
      this.initCommittee(committeeList[i]);
    }
  }

  initCommittee(committee) {
    let eachCommittee = `
        <div class="each-committee" id="${committee.name}">
                        <h5 class="committee-name">${committee.name}</h5>
                        <button class="remove-committee-btn">상임위 삭제</button>
                        <ul>
      `;

    const assemblymanList = committee.assemblymanList;

    for(let i = 0 ; i < assemblymanList.length ; i++) {
      eachCommittee += `
            <li>
                <p class="name">${assemblymanList[i].name}</p>
                <button class="remove-assemblyman-btn">의원 삭제</button>
                <br>
                <p class="status-label">응답 상태</p>
                <select class="participation-status">
                    <option data-item="no-response">무응답</option>
                    <option data-item="accept">참여</option>
                    <option data-item="reject">거절</option>
                </select>
                <textarea class="comment" placeholder="의원 한마디"></textarea>
            </li>`;
    }

    eachCommittee += `
          </ul>
          <hr>
          <input class="add-assemblyman">
          <button class="add-assemblyman-btn">의원 추가</button>
        </div>
      `;

    document.querySelector('#label-add-committee').insertAdjacentHTML('beforebegin', eachCommittee);
  }

  addCommittee() {
    const name = this.inputAddCommittee.value;

    fetch(`/administrator/committees/${name}`, {
      method: 'GET',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json'
      })
    }).then(res => {
      if(res.status != 200) {
        return;
      }
      return res.json();
    }).then(json => {
      let eachCommittee = `
        <div class="each-committee" id="${name}">
                        <h5 class="committee-name">${name}</h5>
                        <button class="remove-committee-btn">상임위 삭제</button>
                        <ul>
      `;


      for(let i = 0 ; i < json.length ; i++) {
        eachCommittee += `
            <li>
                <p class="name">${json[i].name}</p>
                <button class="remove-assemblyman-btn">의원 삭제</button>
                <br>
                <p class="status-label">응답 상태</p>
                <select class="participation-status">
                    <option data-item="no-response">무응답</option>
                    <option data-item="accept">참여</option>
                    <option data-item="reject">거절</option>
                </select>
                <textarea class="comment" placeholder="의원 한마디"></textarea>
            </li>`;
      }

      eachCommittee += `
          </ul>
          <hr>
          <input class="add-assemblyman">
          <button class="add-assemblyman-btn">의원 추가</button>
        </div>
      `;

      document.querySelector('#label-add-committee').insertAdjacentHTML('beforebegin', eachCommittee);
    });
  }

  addAssemblyman(target) {
    const eachCommittee = target.closest('div.each-committee');
    const name = eachCommittee.querySelector('input.add-assemblyman').value;
    const namePattern = /^[가-힣]{2,4}$/;
    if(!namePattern.test(name)) {
      return;
    }

    const assemblymanLi = `
        <li>
            <p class="name">${name}</p>
            <button class="remove-assemblyman-btn">의원 삭제</button>
            <br>
            <p class="status-label">응답 상태</p>
            <select class="participation-status">
                <option data-item="no-response">무응답</option>
                <option data-item="accept">참여</option>
                <option data-item="reject">거절</option>
            </select>
            <textarea class="comment" placeholder="의원 한마디"></textarea>
        </li>
    `;
    eachCommittee.querySelector('ul').insertAdjacentHTML('beforeend', assemblymanLi);
  }

  updateProject() {
    const committeeList = document.querySelectorAll('div.each-committee');
    const committeesJson = {
      committeeList: this.committeeListJson(committeeList),
      date: document.querySelector('#committee-date').value
    };

    let data = {
      'matching_start_date': this.matchingPeriod.value,
      'matching_end_date': this.matchingPeriodEnd.value,
      'participations_goal_count': this.goalCount.value,
      'running_platform_url': this.platformUri.value,
      'committees': JSON.stringify(committeesJson),
      'notice1': this.notice1.value,
      'notice2': this.notice2.value,
      'notice3': this.notice3.value
    };

    if(this.status.value !== 'none') {
     data.status = this.status.value;
    }


    console.log(data);
    const id = document.querySelector('h3').id;
    fetch(`/administrator/projects/${id}`, {
      method: 'PUT',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      }),
      body: JSON.stringify(data)
    }).then(res => {
      if(res.status === 200) {
        alert('변경 완료');
        location.href = '/administrator/projects';
      }
    });
  }

  committeeListJson(committeeList) {
    let committeeListJson = [];
    for(let i = 0 ; i < committeeList.length ; i++) {
      let committeeJson = {
        name: committeeList[i].querySelector('h5.committee-name').innerText,
        assemblymanList : this.assemblymanListJson(committeeList[i].querySelectorAll('li'))
      };
      committeeListJson.push(committeeJson);
    }
    return committeeListJson;
  }

  assemblymanListJson(assemblymanList) {
    let assemblymanListJson = [];
    for(let i = 0 ; i < assemblymanList.length ; i++) {
      let assemblymanJson = {
        name: assemblymanList[i].querySelector('p.name').innerText,
        status: assemblymanList[i].querySelector('select.participation-status').value,
        comment: assemblymanList[i].querySelector('textarea.comment').value
      };
      assemblymanListJson.push(assemblymanJson);
    }
    return assemblymanListJson
  }
  
  removeCommittee(target) {
    target.closest('.each-committee').remove();
  }

  removeCongressmen(target) {
    target.closest('li').remove();
  }
}

export default adminProject;
