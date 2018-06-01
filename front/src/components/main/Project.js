import React, { Component } from 'react';

class Project extends Component {
  constructor(props) {
    super(props);
  }

  componentWillMount() {
    this.projects = this.props.list.map((projectData, i) => {
      let className = 'each-project';
      let status = '';
      switch (projectData.step) {
        case '시민참여':
          status = 'participation';
          break;
        case '매칭실패':
          status = 'fail';
          break;
        case '입법활동':
          status = 'active';
          break;
      }

      if(i === parseInt(this.props.projectCount) - 1) {
        className += ' bottom';
      }
      const percentageStyle = {
        width: projectData.percentage + '%'
      };

      return <div className={className} key={i}>
        <div className="project-img">
          <div className={'status ' + status}>{projectData.step}</div>
        </div>
        <div className="project-summary">
          <h5>{projectData.title}</h5>
          <div className="percentage-zone"><div className="percentage"  style={percentageStyle}></div></div>
          <p className="count">{projectData.count}</p>
          <p className="percentage-number">{projectData.percentage + '%'}</p>
        </div>
      </div>
    });
  }

  render() {
    return (
      <div className={'project ' + this.props.subject}>
        <hr/>
        <h4>{this.props.children}</h4>
        <h5>{this.props.desc}</h5>
        {this.projects}
      </div>
    )
  }
}

export default Project;