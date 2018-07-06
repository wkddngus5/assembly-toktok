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
      return (
        <a href={'/projects/' + project.id}>
          <li key={i} className="project">
            <div className="project-img">
              <div className="status">시민참여</div>
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