// 팔로잉 목록 모달창 열기
function openFollowingModal() {
  var modal = document.getElementById("followingModal");
  modal.style.display = "block";

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  }
}

// 팔로잉 목록 모달창 닫기
function closeFollowingModal() {
  var modal = document.getElementById("followingModal");
  modal.style.display = "none";
}
