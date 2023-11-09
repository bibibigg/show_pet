var textarea = document.getElementById('myTextarea');
var charCount = document.getElementById('charCount');
var maxLength = 50; // 최대 글자 수 지정

textarea.addEventListener('input', function () {
  var text = textarea.value;
  var currentLength = text.length;

  if (currentLength > maxLength) {
    textarea.value = text.substring(0, maxLength); // 최대 글자 수로 제한
    currentLength = maxLength;
  }

  charCount.textContent = currentLength + ' / ' + maxLength + ' 글자';
});
