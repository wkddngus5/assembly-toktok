import React, { Component } from 'react';
import './Footer.css';

class Footer extends Component {

  render() {
    return (
      <footer className="footer">
        <div className="left">
          <a href="#">개인정보취급방침</a>
          <a href="#">이용약관</a>
        </div>
        <div className="right">
          <h6>info@toktok.io</h6>
          <ul>
            <li className="facebook"></li>
            <li className="twitter"></li>
            <li className="kakao"></li>
          </ul>
        </div>
      </footer>
    )
  }
}

export default Footer;