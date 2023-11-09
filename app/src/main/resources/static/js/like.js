function openLikeModal() {
  var modal = document.getElementById("likeModal");
  modal.style.display = "block";

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  }
}

// 좋아요 목록 모달창 닫기
function closeLikeModal() {
  var modal = document.getElementById("likeModal");
  modal.style.display = "none";
}
