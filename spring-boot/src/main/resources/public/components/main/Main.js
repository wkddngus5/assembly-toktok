import React, { Component } from 'react';

import Header from '../header/Header';
import Footer from '../footer/Footer';
import Carousel from './Carousel';
import ProjectList from './ProjectList';

class Main extends Component {
  render() {
    return <div>
      <Header/>
      <Carousel/>
      <ProjectList/>
      <Footer/>
    </div>
  }
}

export default Main;