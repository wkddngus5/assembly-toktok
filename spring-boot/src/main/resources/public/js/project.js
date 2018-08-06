class project {
  constructor() {
    this.infoList = document.querySelectorAll('nav.info-subject button');
    this.nowActiveInfo = document.querySelector('nav.info-subject button.active');
    this.nowVisibleInfo = document.querySelector('div.info .is-visible');
    this.dim = document.querySelector('div.dim');
    this.infoModal = document.querySelector('div.info-modal');
    this.statusZone = document.querySelector('ul.status-zone');
    this.timelineList = document.querySelectorAll('li.each-timeline');
    this.showingGuide = null;
    this.id = parseInt(document.querySelector('h2').getAttribute('data-item'));
    this.countTag = document.querySelector('p.count');

    this.toggleGuide = this.toggleGuide.bind(this);
    this.closeGuide = this.closeGuide.bind(this);
    this.init();
  };

  init() {
    this.percentageSet();
    this.statusSet();
    this.initCommittees();
    document.querySelector('.join-btn').addEventListener('click', e => {
      this.join(e.target);
    });

    document.querySelector('nav.info-subject').addEventListener('click', e => {
      if(e.target.tagName === 'BUTTON'){
        this.switchInfo(e.target);
      }
    });

    document.querySelector('.submit-comment-btn').addEventListener('click', e => {
      this.postComment();
    });

    document.querySelector('ul.discussion-list.new').addEventListener('click', e => {
      const target = e.target;
      if(target.classList.contains('delete')) {
        this.deleteComment(target);
      } else if(target.classList.contains('likes')) {
        this.likeComment(target);
      }
    });

    this.hideLastTimelineOuter();
    document.querySelector('ul.status-zone').addEventListener('click', this.toggleGuide);
    document.addEventListener('click', this.closeGuide);
    this.initLikes();
  };

  initLikes() {
    const likes = document.querySelectorAll('#likes-zone p');
    for(let i = 0 ; i < likes.length ; i++) {
      const id = likes[i].innerText;
      console.log('id', id);
      const like = document.querySelector(`#like-${id}`);
      if(like !== null) {
        like.classList.add('is-active');
      }
    }
  }

  deleteComment(target) {
    const li = target.closest('.each-discussion');
    const commentId = li.getAttribute('data-item');
    fetch(`/projects/${this.id}/comments/${commentId}`, {
      method: 'DELETE',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      if(res.status === 406) {
        alert('본인이 작성한 댓글만 삭제할 수 있습니다.');
        return;
      } else if(res.status === 200) {
        li.remove();
      }
    });
  }

  likeComment(target) {
    const li = target.closest('.each-discussion');
    const commentId = li.getAttribute('data-item');

    fetch(`/projects/${this.id}/comments/${commentId}/likes`, {
      method: 'PATCH',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      if(res.status === 201) {
        console.log('좋아요');
        target.classList.add('is-active');
        return;
      } else if(res.status === 200) {
        target.classList.remove('is-active');
        console.log('좋아요 취소 ');
      }
    });
  }

  postComment() {
    const data =  {
      'commentable_id': document.querySelector('h2').getAttribute('data-item'),
      'body': document.querySelector('.input-comment').value
    };

    fetch(`/projects/${this.id}/comments`, {
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
      const newComment = `
            <li class="each-discussion" data-item="{{this.id}}">
                <table>
                    <tr>
                        <td class="left">
                            <div class="writer-info-zone">
                              <div class="profile"></div><br>
                                <p class="nickname">${json.writer.nickname}</p>
                                <p class="written-date">${json.created_at}</p>
                              </div><br>
                              <div class="discussion-info">
                                <!--<button class="reply">답글 6</button>-->
                                <button class="likes">${json.likes_count}</button>
                              </div>
                        </td>
                        <td class="right">
                            <p class="discussion-contents">
                                ${json.body}
                            </p>
                        </td>
                    </tr>
                </table>
            </li>`
      document.querySelector('ul.discussion-list.new').insertAdjacentHTML('beforeend', newComment);
    });
  }

  initCommittees() {
    const assembly = document.querySelector('div.assembly');
    if(assembly == null) {
      return;
    }
    const committees = JSON.parse(assembly.getAttribute('data-item')).committeeList;
    const ul = document.querySelector('#committee');
    for(let i = 0 ; i < committees.length ; i++) {
      const committee = `<li>${committees[i].name}</li>`;
      ul.insertAdjacentHTML('beforeend', committee);
      this.insertAssemblymen(committees[i].assemblymanList);
    }
  }

  insertAssemblymen(assemblymanList) {
    for(let i = 0 ; i < assemblymanList.length ; i++) {
      const ul = document.querySelector('#assembly-man-list');
      if(assemblymanList[i].status === '참여') {
        const assemblymanLi = `
            <li class="assembly-man">
                <div class="profile-img"></div>
                <p>${assemblymanList[i].name} 의원</p>
            </li>
        `;
        ul.insertAdjacentHTML('beforeend', assemblymanLi);
      }
    }
  }

  closeGuide(e) {
    if(!this.showingGuide || e.target.tagName === 'LI') {
      return;
    }

    e.preventDefault();
    this.showingGuide.classList.remove('is-visible');
    this.showingGuide = null;
  }

  toggleGuide(e) {
    if(e.target.classList.contains('active')) {
      return;
    }

    e.target.querySelector('div.guide').classList.add('is-visible');
    if(this.showingGuide) {
      this.showingGuide.classList.remove('is-visible');
    }

    this.showingGuide = document.querySelector('div.guide.is-visible');
  }

  hideLastTimelineOuter() {
    if(this.timelineList.length < 1) {
      return;
    }
    this.timelineList[this.timelineList.length - 1].querySelector('.outer-border').classList.add('white');
  }

  statusSet() {
    let nowStatus = this.statusZone.getAttribute('data-item');
    switch (nowStatus) {
      case 'running':
        this.statusZone.querySelector('.lawmaking').classList.add('active');
        break;
      case 'fail':
        let step = this.statusZone.querySelector('.matching');
        step.classList.add('active');
        step.innerText = '매칭실패';
        break;
      default:
        this.statusZone.querySelector('.participation').classList.add('active');
    }
  }

  modalOn() {
    this.dim.classList.add('is-visible');
    this.infoModal.classList.add('is-visible');
  }

  modalOff() {
    this.dim.classList.remove('is-visible');
    this.infoModal.classList.remove('is-visible');
  }

  percentageSet() {
    const count = this.countTag.innerText;
    const goalCount = document.querySelector('.percentage-number').getAttribute('data-item');

    const percentage = (parseInt(count) / parseInt(goalCount) * 100).toFixed(1);

    document.querySelector('.percentage-zone .percentage').style.width =
      (percentage > 100.0 ? 100.0 : percentage) + '%';
    document.querySelector('.percentage-number').innerText = percentage + '%';
  }

  switchInfo(target) {
    this.nowActiveInfo.classList.remove('active');
    this.nowActiveInfo = target;
    this.nowActiveInfo.classList.add('active');

    let clickedData = target.getAttribute('data-item');
    this.nowVisibleInfo.classList.remove('is-visible');
    this.nowVisibleInfo = document.querySelector(`div.info .info-contents.${clickedData}`);
    this.nowVisibleInfo.classList.add('is-visible');
  }

  join(button) {
    fetch(`/projects/${this.id}/join`, {
      method: 'PATCH',
      credentials: 'same-origin',
      headers: new Headers({
        'accept': 'application/json',
        'content-type': 'application/json',
      })
    }).then(res => {
      this.countTag = document.querySelector('p.count');
      if(res.status === 201) {
        button.innerText = '참여취소';
        button.classList.add('is-active');
        this.countTag.innerText = parseInt(this.countTag.innerText) + 1;
      } else if(res.status === 202) {
        button.innerText = '참여하기';
        button.classList.remove('is-active');
        this.countTag.innerText = parseInt(this.countTag.innerText) - 1;
      } else if(res.status === 401) {
        window.location.href = '/login';
      }
    });
  }
}

export default project;
