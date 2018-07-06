import React from 'react';
import ReactDOM from 'react-dom';
import ProjectListContainer from '../js/components/ProjectListContainer';

import 'promise-polyfill/src/polyfill';
import 'whatwg-fetch';

ReactDOM.render(<ProjectListContainer/> , document.getElementById('root'));
