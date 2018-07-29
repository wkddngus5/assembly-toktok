!function(e){var t={};function n(r){if(t[r])return t[r].exports;var o=t[r]={i:r,l:!1,exports:{}};return e[r].call(o.exports,o,o.exports,n),o.l=!0,o.exports}n.m=e,n.c=t,n.d=function(e,t,r){n.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:r})},n.r=function(e){"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},n.t=function(e,t){if(1&t&&(e=n(e)),8&t)return e;if(4&t&&"object"==typeof e&&e&&e.__esModule)return e;var r=Object.create(null);if(n.r(r),Object.defineProperty(r,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)n.d(r,o,function(t){return e[t]}.bind(null,o));return r},n.n=function(e){var t=e&&e.__esModule?function(){return e.default}:function(){return e};return n.d(t,"a",t),t},n.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},n.p="/dist/",n(n.s=39)}({0:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}();var o=function(){function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),this.searchForm=document.querySelector("form#search-zone"),this.inputSearch=document.querySelector("#input-search"),this.init()}return r(e,[{key:"init",value:function(){var e=this;this.inputSearch.addEventListener("focus",function(){e.searchForm.classList.add("focus"),e.inputSearch.classList.add("focus")}),this.inputSearch.addEventListener("focusout",function(){e.searchForm.classList.remove("focus"),e.inputSearch.classList.remove("focus")}),this.inputSearch.addEventListener("keydown",function(e){13===e.keyCode&&(e.preventDefault(),location.href="/projects/search?keyword="+e.target.value)})}}]),e}();t.default=o},39:function(e,t,n){"use strict";var r=i(n(0)),o=i(n(40));function i(e){return e&&e.__esModule?e:{default:e}}new r.default,new o.default},40:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}();var o=function(){function e(){!function(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}(this,e),this.pages=document.querySelectorAll(".page"),this.main=document.querySelector("main"),this.projectImageInput=document.querySelector("input.project-img"),this.projectImagePreview=document.querySelector("div.image-preview"),this.completeBtn=document.querySelector("button.complete"),this.init()}return r(e,[{key:"init",value:function(){var e=this;CKEDITOR.replace("editor1",{toolbar:[{name:"clipboard",items:["Undo","Redo"]},{name:"styles",items:["Format","Font","FontSize"]},{name:"colors",items:["TextColor","BGColor"]},{name:"align",items:["JustifyLeft","JustifyCenter","JustifyRight","JustifyBlock"]},"/",{name:"basicstyles",items:["Bold","Italic","Underline","Strike","RemoveFormat","CopyFormatting"]},{name:"links",items:["Link","Unlink"]},{name:"insert",items:["Image","Table","Video"]},{name:"tools",items:["Maximize"]},{name:"editing",items:["Scayt"]}],extraAllowedContent:"h3{clear};h2{line-height};h2 h3{margin-left,margin-top}",extraPlugins:"print,format,font,colorbutton,justify,uploadimage",uploadUrl:"/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files&responseType=json",filebrowserBrowseUrl:"/ckfinder/ckfinder.html",filebrowserImageBrowseUrl:"/ckfinder/ckfinder.html?type=Images",filebrowserUploadUrl:"/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files",filebrowserImageUploadUrl:"/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images",height:560,removeDialogTabs:"image:advanced;link:advanced"}),document.querySelectorAll("button.next").forEach(function(t,n){t.addEventListener("click",function(t){e.main.style.marginTop=e.pageHeight*n*-1+100+"px"})}),this.completeBtn.addEventListener("click",function(e){e.preventDefault();var t={proposer:document.querySelector("#name").value,proposer_email:document.querySelector("#email").value,proposer_phone:document.querySelector("#phone").value,proposer_description:document.querySelector("#introduce").value,title:document.querySelector("#title").value,summary:document.querySelector("#desc").value,body:CKEDITOR.instances.editor1.getData()};console.log("data: ",t),fetch("/projects",{method:"POST",credentials:"same-origin",headers:new Headers({accept:"application/json","content-type":"application/json"}),body:JSON.stringify(t)}).then(function(e){return 200===e.status&&(window.location="/"),e.json()}).then(function(e){console.log(e)})}),document.querySelector("ul.category-list").addEventListener("click",function(t){"LI"===t.target.tagName&&e.toggleCategoryClick(t.target)}),this.projectImagePreview.addEventListener("click",function(t){e.projectImageInput.click()}),this.projectImageInput.addEventListener("change",function(t){e.changeProjectImage(t.target)})}},{key:"changeProjectImage",value:function(e){var t=this,n=e.files[0],r=new FileReader;r.addEventListener("load",function(){t.projectImagePreview.innerHTML="",t.projectImagePreview.style.backgroundImage="url("+r.result+")"},!1),n&&r.readAsDataURL(n)}},{key:"toggleCategoryClick",value:function(e){e.classList.contains("is-checked")?e.classList.remove("is-checked"):e.classList.add("is-checked")}},{key:"resetPageHeight",value:function(){var e=this;this.pageHeight=.75*window.innerHeight-166,this.pages.forEach(function(t){t.style.height=e.pageHeight+"px"})}}]),e}();t.default=o}});