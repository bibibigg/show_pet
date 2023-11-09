function previewImage(input) {
  const imagePreview = document.getElementById('imagePreview');

  if (input.files && input.files[0]) {
    const reader = new FileReader();

    reader.onload = function (e) {
      const preview = document.createElement('img');
      preview.src = e.target.result;
      preview.style.maxWidth = '100%';
      imagePreview.innerHTML = '';
      imagePreview.appendChild(preview);
    };

    reader.readAsDataURL(input.files[0]);
  } else {
    // 파일이 선택되지 않은 경우 이미지 미리보기를 제거
    imagePreview.innerHTML = '';
  }
}
