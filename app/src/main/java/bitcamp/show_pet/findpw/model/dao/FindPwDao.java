package bitcamp.show_pet.findpw.model.dao;

import bitcamp.show_pet.findpw.service.FindPwHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FindPwDao {

  @Autowired
  private JdbcTemplate jdbcTemplate; // Spring JDBC를 사용하여 데이터베이스와 상호작용

  @Autowired
  private FindPwHashService findPwHashService;

  public void updatePassword(String email, String newPassword) {
    String hashedPassword = findPwHashService.hashPassword(newPassword);

    // 데이터베이스에서 사용자의 해싱된 비밀번호를 업데이트하는 SQL 쿼리를 실행합니다.
    String sql = "UPDATE member SET password = ? WHERE email = ?";
    jdbcTemplate.update(sql, hashedPassword, email);
  }

  public boolean isEmailExists(String email) {
    String sql = "SELECT COUNT(*) FROM member WHERE email = ?";
    int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
    return count > 0;
  }

  // 다른 데이터베이스 관련 메서드를 필요에 따라 추가할 수 있습니다.
}
