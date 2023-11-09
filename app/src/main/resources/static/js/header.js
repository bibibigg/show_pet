// 알림 모달 열기
function openHeaderNotificationModal() {
  document.getElementById('headerNotificationModal').style.display = 'block';
}

// 알림 모달 닫기
function closeHeaderNotificationModal() {
  document.getElementById('headerNotificationModal').style.display = 'none';
}

// 페이지가 로드되면 저장된 알림을 불러옵니다.
document.addEventListener('DOMContentLoaded', function () {
  loadHeaderNotifications();

  const deleteAllNotificationsButton = document.getElementById('deleteAllNotificationsButton');
  if (deleteAllNotificationsButton) {
    deleteAllNotificationsButton.addEventListener('click', function () {
      fetch('/member/notifications/deleteAll', {
        method: 'POST',
      })
      .then(response => {
        if (!response.ok) {
          return Promise.reject('Error deleting notifications: ' + response.statusText);
        }
        const notiContainer = document.querySelector("#notificationList");
        while (notiContainer.firstChild) {
          notiContainer.removeChild(notiContainer.firstChild);
        }
      })
      .catch(error => {
        console.error(error);
      });
    });
  }
});

function loadHeaderNotifications() {
  fetch('/member/headerNotifications', {
    method: 'GET',
  })
  .then(response => {
    if (!response.ok) {
      return Promise.reject('Error loading notifications: ' + response.statusText);
    }
    return response.json();
  })
  .then(notifications => {
    const notiContainer = document.querySelector("#notificationList");
    notifications.forEach(notification => {
      const newNoti = document.createElement("li");
      newNoti.textContent = notification.content;
      notiContainer.appendChild(newNoti);
    });
  })
  .catch(error => {
    console.error(error);
  });
}

window.addEventListener('load', function () {
  const alarmIcon = document.getElementById('alarmIcon');
  if (!alarmIcon) {
    console.error('alarmIcon does not exist!');
    return;
  }

  alarmIcon.addEventListener('click', function () {
    openHeaderNotificationModal();
  });

  const eventSource = new EventSource('/member/notifications/stream');
  eventSource.addEventListener('alarm', function (event) {
    const originalSrc = alarmIcon.src;
    alarmIcon.src = '/images/new-alarm.gif'; // 알림이 왔을 때의 아이콘 (이미지 or GIF) 경로

    setTimeout(function() {
      alarmIcon.src = originalSrc; // 원래의 아이콘으로 돌려놓기
    }, 2000); // 1초 후에 원래 아이콘으로 복구

    const data = JSON.parse(event.data);
    const notiContainer = document.querySelector("#notificationList");
    const newNoti = document.createElement("li");
    newNoti.textContent = data.content;
    if (notiContainer.firstChild) {
      notiContainer.insertBefore(newNoti, notiContainer.firstChild);
    } else {
      notiContainer.appendChild(newNoti);
    }
  });

  eventSource.onerror = function (event) {
    console.error('EventSource failed:', event);
    eventSource.close();
  };
});
