import React, { Component } from 'react';
import './Header.css';
import Hamburger from '../../img/hamburger.png';

class Header extends Component {
  render() {
    const menuStyle = {
      backgroundImage: `url(${Hamburger})`
    };

    return (
      <header className="header">
        <a>국회톡톡</a>
        <div className="right-elements">
          <div className="search-zone">SEARCH</div>
          <div className="menu" style={menuStyle}></div>
        </div>
      </header>
    )
  }
}

export default Header;