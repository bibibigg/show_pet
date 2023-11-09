const myPostsButton = document.getElementById('myPostsButton');
const likedPostsButton = document.getElementById('likedPostsButton');
const bookmarkedPostsButton = document.getElementById('bookmarkedPostsButton');

const myPosts = document.getElementById('myPosts');
const likedPosts = document.getElementById('likedPosts');
const bookmarkedPosts = document.getElementById('bookmarkedPosts');

myPostsButton.addEventListener('click', () => {
  myPosts.style.display = 'block';
  likedPosts.style.display = 'none';
  bookmarkedPosts.style.display = 'none';
});

likedPostsButton.addEventListener('click', () => {
  myPosts.style.display = 'none';
  likedPosts.style.display = 'block';
  bookmarkedPosts.style.display = 'none';
});

bookmarkedPostsButton.addEventListener('click', () => {
  myPosts.style.display = 'none';
  likedPosts.style.display = 'none';
  bookmarkedPosts.style.display = 'block';
});

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

// 팔로우 버튼
$(".followButton").on("click", function () {
  let memberId = $(this).data("member-id");
  let isFollowed = $(this).data("is-followed");

  $.ajax({
    url: "/member/" + memberId + "/follow",
    type: "POST",
    success: function (response) {
      if (response.newIsFollowed) {
        $("#followImage-" + memberId).attr("src", "/images/follow_on.png");
        $(".followButton[data-member-id='" + memberId + "']").data("is-followed", true);
      } else {
        $("#followImage-" + memberId).attr("src", "/images/follow_off.png");
        $(".followButton[data-member-id='" + memberId + "']").data("is-followed", false);
      }
    }
  });
});

// 팔로잉 목록 모달창 닫기
function closeFollowingModal() {
  var modal = document.getElementById("followingModal");
  modal.style.display = "none";
}

const notificationsButton = document.getElementById('notificationsButton');
const notificationModal = document.getElementById('notificationModal');

// 알림 목록 모달창 열기
function openNotificationModal() {
  var modal = document.getElementById("notificationModal");
  modal.style.display = "block";

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
      window.onclick = null; // 클릭 이벤트 제거
    }
  };
}

// 알림 목록 모달창 닫기
function closeNotificationModal() {
  var modal = document.getElementById("notificationModal");
  modal.style.display = "none";
}

notificationsButton.addEventListener('click', openNotificationModal);

const deleteAllNotificationsButton = document.getElementById(
    'deleteAllNotificationsButton');

deleteAllNotificationsButton.addEventListener('click', function () {
  if (!confirm('모든 알림을 삭제하시겠습니까?')) {
    return;
  }
  fetch('/member/notifications/deleteAll', {
    method: 'POST',
  })
  .then(response => {
    if (!response.ok) {
      return Promise.reject(
          'Error deleting notifications: ' + response.statusText);
    }
    return response.text();
  })
  .then(text => {
    alert(text);
    // 성공적으로 삭제하면 목록을 비웁니다.
    document.querySelector('#notificationList').innerHTML = '';
  })
  .catch(error => {
    console.error(error);
    alert(error);
  });
});

// 실시간 SSE처리
document.addEventListener("DOMContentLoaded", function () {
  const eventSource = new EventSource('/member/notifications/stream');

  eventSource.addEventListener('alarm', function (event) {
    const data = JSON.parse(event.data);
    const notiContainer = document.querySelector("#notificationList");

    const newNoti = document.createElement("li");
    newNoti.textContent = data.content;

    // 새로운 알림을 상단에 추가
    if (notiContainer.firstChild) {
      notiContainer.insertBefore(newNoti, notiContainer.firstChild);
    } else {
      notiContainer.appendChild(newNoti);
    }
  });

  eventSource.onerror = function (event) {
    eventSource.close();
  };
});
