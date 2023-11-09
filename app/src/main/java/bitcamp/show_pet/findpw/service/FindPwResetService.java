package bitcamp.show_pet.findpw.service;

import bitcamp.show_pet.findpw.model.dao.FindPwDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindPwResetService {
    @Autowired
    DefaultFindPwService userService;

    @Autowired
    FindPwDao findPwDao;

    public void resetPassword(String email) {
        // 1. 이메일이 데이터베이스에 존재하는지 확인
        if (!findPwDao.isEmailExists(email)) {
            throw new IllegalArgumentException("이메일 주소가 유효하지 않습니다.");
        }

        // 1. 임시 비밀번호 생성
        String temporaryPassword = generateTemporaryPassword();

        // 2. 사용자의 이메일 주소를 사용하여 임시 비밀번호 업데이트 (DAO 호출)
        findPwDao.updatePassword(email, temporaryPassword);

        // 3. 사용자에게 임시 비밀번호 이메일로 보내기
        userService.sendTemporaryPasswordEmail(email, temporaryPassword);
    }

    // 임시 비밀번호 생성 메서드 (예: 8자리 무작위 문자열)
    private String generateTemporaryPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder temporaryPassword = new StringBuilder();
        int length = 8;
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            temporaryPassword.append(characters.charAt(index));
        }
        return temporaryPassword.toString();
    }


}
