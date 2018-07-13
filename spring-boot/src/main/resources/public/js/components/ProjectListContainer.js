import React, { Component } from 'react';

import ProjectList from './ProjectList';

class ProjectListContainer extends Component {
  constructor() {
    super();
    this.state = {
      projects: []
    }
    this.scrollFlag = true;
  }


  componentDidMount() {
    this.initProjectList();
    window.addEventListener('scroll', e => {
      const bottomToAddPoint = 450;
      if(window.scrollY + (window.innerHeight * 0.7) > document.body.clientHeight - bottomToAddPoint
      && this.scrollFlag) {
        this.scrollFlag = false;
        this.getProjects(this.by, ++this.page);
        setTimeout(() => {
          this.scrollFlag = true;
        }, 1000)
      }
    });
  }

  initProjectList() {
    const byList = ['new', 'best', 'imminent'];
    this.by = document.location.href.split('by=')[1];
    this.page = 1;

    if(!byList.includes(this.by)) {
      location.href = '/';
    }

    switch (this.by) {
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
    this.getProjects(this.by, this.page);
  }

  getProjects(by, number) {
    fetch('/projects/sorted?by=' + by + '&number=' + number, {
      method: 'GET',
      headers: new Headers({
        'accept': 'application/json'
      })
    })
      .then(res => {
        return res.json();
      })
      .then(json => {
        this.setState({
          projects: this.state.projects.concat(json)
        });
      });
  }

  render() {
    return (
      <div>
        <h2>{this.h2}</h2>
        <div className="filler-zone">
          <select className="category-list">
            <option className="category" data-item="전체">분야별</option>
            <option className="category" data-item="환경">환경</option>
            <option className="category" data-item="인권">인권</option>
            <option className="category" data-item="육아">육아</option>
            <option className="category" data-item="언론">언론</option>
          </select>
        </div>
        <ul className="project-list">
          <ProjectList projects={this.state.projects}></ProjectList>
        </ul>
      </div>
    )
  }
}


export default ProjectListContainer;