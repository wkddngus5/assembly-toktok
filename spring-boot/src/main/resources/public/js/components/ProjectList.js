import React, { Component } from 'react';
// import Project from "../../components/main/Project";

class ProjectList extends Component {
  constructor() {
    super();
  }

  render() {
    const { projects } = this.props;

    const projectList = projects.map((project, i) => {
      const countByGoal = (project.participations_count / project.participations_goal_count * 100).toFixed(2);

      let percentageNumber =
        countByGoal > 100.00 ? '100%' : countByGoal + '%';

      let statusText = '시민참여';
      switch (project.status) {
        case 'running':
          statusText = '입법활동';
          break;
        case 'fail':
          statusText = '매칭실패';
          break;
      }

      return (
        <a href={'/projects/' + project.id}>
          <li key={i} className="project">
            <div className="project-img">
              <div className={"status " + project.status} data-item={project.status}>{statusText}</div>
            </div>
            <div className="project-summary">
              <h5>{project.title}</h5>
              <div className="percentage-zone">
                <div className="percentage" style={{width: percentageNumber}}></div>
              </div>
              <p className="count">{project.participations_count}</p>
              <p className="percentage-number" data-item={project.participations_goal_count}>{countByGoal + '%'}</p>
            </div>
          </li>
        </a>
        );
    });


    return (
      <div>
        {projectList}
      </div>
    )
  }
}

export default ProjectList;