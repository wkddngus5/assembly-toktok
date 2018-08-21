import carousel from '../js/carousel';
import header from '../js/header';
import mainProjects from '../js/mainProjects';
import 'nodelist-foreach-polyfill';

(function () {
  const throttle = function(type, name, obj) {
    obj = obj || window;
    let running = false;
    const func = function() {
      if (running) { return; }
      running = true;
      requestAnimationFrame(function() {
        obj.dispatchEvent(new CustomEvent(name));
        running = false;
      });
    };
    obj.addEventListener(type, func);
  };

  /* init - you can init any event */
  throttle("resize", "optimizedResize");

  new carousel();
  new header();
  new mainProjects();
})();
