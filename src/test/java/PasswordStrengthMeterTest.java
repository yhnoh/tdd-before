import chap02.PasswordStrength;
import chap02.PasswordStrengthMeter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 1. 길이가 8글자 이상
 * 2. 0~9 사이의 숫자 포함
 * 3. 대문자 포함
 *
 *
 * 1개 이하로 만족하면 WEAK
 * 2개를 만족하면 NORAML
 * 전부 만족하면 STRONG
 *
 * TDD 흐름
 * 테스트 -> 코딩 -> 리펙토링
 *
 * 범용적 기능을 먼저 구현하고 차근차근 제약조건을 추가해야한다.
 * 제약조건을 먼저 구현하면 테스트 코드 구현이 힘들어 진다.
 */
public class PasswordStrengthMeterTest {

    PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String pwd, PasswordStrength strength){
        PasswordStrength result = meter.meter(pwd);
        Assertions.assertEquals(strength, result);

    }
    @Test
    @DisplayName("모든 조건을 충족 '강함'")
    void password_all_then_strong(){

        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!Add", PasswordStrength.STRONG);

    }

    @Test
    @DisplayName("길이가 8글자 미만이고 나머지 조건은 만족")
    void password_length_exception_then_normal(){
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
    }
    @Test
    @DisplayName("숫자를 포함하지 않고 나머지 조건은 충족")
    void password_number_exception_then_normal(){
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("값이 없는 경우")
    void password_empty_exception_then_invalid(){
        assertStrength(null, PasswordStrength.INVALID);
        assertStrength("", PasswordStrength.INVALID);

    }

    @Test
    @DisplayName("대문자를 포함하지 않고 나머지 조건을 충족")
    void password_not_uppercase_exception_then_normal(){
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    @DisplayName("길이가 8글자 이상인 조건만 충족")
    void password_only_length_exception_then_weak(){
        assertStrength("abcdefgh", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("숫자만 포함한 경우")
    void password_only_number_exception_then_weak() {
        assertStrength("12345", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("대문자만 포함한 경우")
    void password_only_uppercase_exception_then_weak() {
        assertStrength("ABCDEF", PasswordStrength.WEAK);
    }

    @Test
    @DisplayName("아무 조건을 만족하지 않는 경우")
    void password_not_match_exception_then_weak() {
        assertStrength("abc", PasswordStrength.WEAK);
    }

}
