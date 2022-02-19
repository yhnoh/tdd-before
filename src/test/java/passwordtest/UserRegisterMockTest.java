package passwordtest;


import chap02.EmailNotifier;
import chap02.UserRegister;
import chap02.WeakPasswordChecker;
import chap02.WeakPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

/**
 * Mockito : 모이객체 생성의 역할을 한다.
 */
public class UserRegisterMockTest {
    private UserRegister userRegister;
    private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private FakeUserRepository fakeUserRepository = new FakeUserRepository();
    private EmailNotifier emailNotifier = Mockito.mock(EmailNotifier.class);

    @BeforeEach
    public void setup(){
        userRegister = new UserRegister(mockPasswordChecker, fakeUserRepository, emailNotifier);
    }

    /**
     * 대역객체가 기대하는대로 상호작용했는지를 확인하는 것이 모의객체의 주요 기능
     *
     */
    @Test
    public void 약한암호면가입실패(){
        //행위에 대한 결과를 미리셋팅할 수 있다.
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pwd")).willReturn(true);

        Assertions.assertThrows(WeakPasswordException.class, () -> {
            userRegister.register("id", "pwd", "email@newsystock.com");
        });
    }

    /**
     * 특정 메소드가 호출됬는지 검즘
     */
    @Test
    public void 회원가입시암호검사테스트(){
        userRegister.register("id", "pwd","email");

        BDDMockito.then(mockPasswordChecker).should().checkPasswordWeak(BDDMockito.anyString());

    }

    /**
     * 특정 메소드가 호출되었을때 ArgumentCaptor를 이용하면 전달된 인자를 구할 수 있다.
     */
    @Test
    public void 가입하면메일을전송함(){
        userRegister.register("id", "pwd","email");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(emailNotifier).should().sendRegisterEmail(captor.capture());
        String captorEmail = captor.getValue();
        Assertions.assertEquals("email", captorEmail);
    }
}
