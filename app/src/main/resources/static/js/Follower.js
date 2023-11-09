// 팔로워 목록 모달창 열기
function openFollowerModal() {
  var modal = document.getElementById("followerModal");
  modal.style.display = "block";

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  }
}

// 팔로워 목록 모달창 닫기
function closeFollowerModal() {
  var modal = document.getElementById("followerModal");
  modal.style.display = "none";
}
