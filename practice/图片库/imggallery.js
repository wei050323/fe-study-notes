function showPic(whichpic) {
  var source = whichpic.getAttribute("href");
  var placeholder = document.getElementById("placeholder");
  placeholder.setAttribute("src", source);
  var textsource = whichpic.getAttribute("title");
  var text = document.getElementById("text");
  text.firstChild.nodeValue = textsource;
}
function countBodyChildren() {
  var body_element = document.getElementsByTagName("body")[0];
  alert(body_element.childNodes.length);
}
window.onload = countBodyChildren;
