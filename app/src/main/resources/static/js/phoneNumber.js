var telInput = document.getElementById('tel');
var charCount = document.getElementById('charCount');
var maxLength = 11; // 최대 길이 11자 지정

telInput.addEventListener('input', function () {
    var text = telInput.value;
    var currentLength = text.length;

    if (currentLength > maxLength) {
        telInput.value = text.substring(0, maxLength); // 최대 길이로 제한
        currentLength = maxLength;
    }

    charCount.textContent = currentLength + ' / ' + maxLength + ' 글자';
});