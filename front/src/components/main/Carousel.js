import React, { Component } from 'react';
import './Carousel.css';


class Carousel extends Component {
  constructor(props) {
    super(props);
    this.nowIndex = 1;
  }

  componentWillMount() {
    this.pageList = [
      {title: 'page1'},
      {title: 'page2'},
      {title: 'page3'}
    ];

    this.pageListComponents = this.pageList.map((page, i) => {
        return <li key={i + 1} className={'carousel-page page-' + (i + 1)} >{page.title}</li>
    });

    this.carouselIndexListComponents = this.pageList.map((page, i) => {
      if(i === 0) {
        return <li key={i + 1} className="carousel-index index-1 is-checked" onClick={ e => {
          this.movePage(e.target);
        }} data-item="1"></li>
      } else {
        return <li key={i + 1} className={'carousel-index index-' + (i + 1)} onClick={ e => {
          this.movePage(e.target);
        }} data-item={i + 1}></li>
      }
    });
  }

  componentDidMount() {
    document.querySelector('.carousel-page-list').style.width = (this.pageList.length * 100) + '%';
    this.pageList = document.querySelectorAll('.carousel-page');
    this.indexList = document.querySelectorAll('.carousel-index');
    for(let i = 0 ; i < this.pageList.length ; i++) {
      this.pageList[i].style.width = 100/this.pageList.length + '%';
    }

  }

  movePage(target) {
    this.indexList[this.nowIndex - 1].classList.remove('is-checked');
    this.nowIndex = target.getAttribute('data-item');
    for(let i = 0 ; i < this.pageList.length ; i++) {
      if(i === this.nowIndex - 1) {
        this.pageList[i].style.width = 100/this.pageList.length + '%';
        this.indexList[i].classList.add('is-checked');
      } else {
        this.pageList[i].style.width = '0';
      }
    }
  }

  render() {
    return (
      <div className="main-carousel">
        <ul className="carousel-page-list">
          {this.pageListComponents}
        </ul>
        <ul className="carousel-index-list">
          {this.carouselIndexListComponents}
        </ul>
      </div>
    )
  }
}

export default Carousel;