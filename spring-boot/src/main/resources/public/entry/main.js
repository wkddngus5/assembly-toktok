import carousel from '../js/carousel';
import header from '../js/header';
import mainProjects from '../js/mainProjects';
import 'nodelist-foreach-polyfill';

(function () {
  new carousel();
  new header();
  new mainProjects();
})();

