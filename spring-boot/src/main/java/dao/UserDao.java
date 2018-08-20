package dao;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email = ?1 AND provider = ?2", nativeQuery = true)
    List<User> findByEmailAndProvider(String email, String provider);

    @Query(value = "SELECT * FROM users WHERE reset_password_token = ?1", nativeQuery = true)
    User getUserByPasswordResetToken(String token);

    @Query(value = "SELECT * FROM users WHERE provider = ?1 AND uid = ?2", nativeQuery = true)
    User findByProviderId(String provider, String uid);

    @Query(value = "SELECT * FROM users WHERE role = 'staff'", nativeQuery = true)
    List<User> findStaff();

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET reset_password_token = ?1, reset_password_sent_at = ?2 WHERE id = ?3", nativeQuery = true)
    void createPasswordResetTokenForUser(String token, String createDt, Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET reset_password_token = NULL, reset_password_sent_at = NULL, encrypted_password = ?1, updated_at = ?2 WHERE id = ?3", nativeQuery = true)
    void changeUserPassword(String password, String createDt, Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET nickname = ?2, image = ?3, updated_at = ?4 WHERE id = ?1", nativeQuery = true)
    void updateUserInformation(long userId, String nickname, String image, String updateDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET encrypted_password = ?2, updated_at = ?3 WHERE id = ?1", nativeQuery = true)
    void updateUserPassword(long userId, String password, String updateDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET sign_in_count = sign_in_count + 1, last_sign_in_at = current_sign_in_at, current_sign_in_at = ?2, last_sign_in_ip = current_sign_in_ip, current_sign_in_ip = ?3  WHERE id = ?1", nativeQuery = true)
    void updateLoginInformation(Long id, String currentDate, String ipAddress);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET role = ?3 WHERE email = ?1 AND provider = ?2", nativeQuery = true)
    void updateUserRole(String email, String provider, String role);
}
