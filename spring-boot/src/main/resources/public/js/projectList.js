class projectList {
  constructor() {
    const byList = ['new', 'best', 'imminent'];
    const by = document.location.href.split('by=')[1];

    if(!byList.includes(by)) {
      location.href = '/';
    }

    this.init(by);
  }

  init(by) {
    switch (by) {
      case 'new':
        this.h2 = '신규 제안';
        break;
      case 'best':
        this.h2 = '베스트 제안';
        break;
      case 'imminent':
        this.h2 = '임박 제안';
        break;
    }

    fetch('/projects/sorted?by=new&number=1', {
      method: 'GET',
      headers: new Headers({
        'accept': 'application/json'
      })
    })
      .then(res => {
        console.log(res);
        return res.json();
      })
      .then(json => {
        console.log(json)
      });
  }
}

new projectList();