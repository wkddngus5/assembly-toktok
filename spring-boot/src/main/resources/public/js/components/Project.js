import React, { Component } from 'react';

class Project extends Component {
  constructor() {
    super();
  }

  render() {
    const { title, count, percentageNumber } = this.props;

    return (
      <a href="/projects/1">
        <li key={i} className="project">
          <div className="project-img">
            <div className="status">시민참여</div>
          </div>
          <div className="project-summary">
            <h5>임신중단 전면 합법화를 촉구합니다.</h5>
            <div className="percentage-zone"><div className="percentage"></div></div>
            <p className="count">1340명</p>
            <p className="percentage-number" data-item="1000">78%</p>
          </div>
        </li>
      </a>
    )
  }
}

export default Project;