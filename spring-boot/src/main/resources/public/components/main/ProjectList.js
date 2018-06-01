import React, { Component } from 'react';

import Project from './Project';
import './Project.css';

class ProjectList extends Component {

  render() {
    this.imminent = [
      {
        title: '임신중단 전면 합법화를 촉구합니다.',
        step: '시민참여',
        count: 1340,
        percentage: 78
      },
      {
        title: '사립유치원 육성법 제정을 요청드립니다.',
        step: '입법활동',
        count: 1340,
        percentage: 78
      },
      {
        title: 'K-POP 법',
        step: '매칭실패',
        count: 1340,
        percentage: 78
      },
      {
        title: '서비스직에게 앉으면서 일 할 권리',
        step: '시민참여',
        count: 1340,
        percentage: 78
      },
      {
        title: '취업준비생 보호법을 제안합니다.',
        step: '시민참여',
        count: 1340,
        percentage: 78
      }
    ];

    this.best = [
      {
        title: '임신중단 전면 합법화를 촉구합니다.',
        step: '시민참여',
        count: 1340,
        percentage: 78
      },
      {
        title: 'K-POP 법',
        step: '매칭실패',
        count: 1340,
        percentage: 78
      },
      {
        title: '취업준비생 보호법을 제안합니다.',
        step: '시민참여',
        count: 1340,
        percentage: 78
      }
    ];

    return (
      <div onClick="projects-list-zone">
        <Project
          desc="마감일이 얼마 남지 않은 제안들을 확인해보세요."
          subject="imminent"
          list={this.imminent}
          projectCount="5">임박 제안</Project>
        <Project
          desc="가장 참여율이 높은 베스트 제안들을 확인해보세요."
          subject="best"
          list={this.best}
          projectCount="3">베스트 제안</Project>
        <Project
          desc="새로운 제안들을 확인해보세요."
          subject="new"
          list={this.best}
          projectCount="3">신규 제안</Project>
      </div>
    )
  }
}

export default ProjectList;