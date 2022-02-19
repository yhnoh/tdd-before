package passwordtest;

import chap02.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class UserRegisterTest {

    private UserRegister userRegister;
    //가짜 객체를 만든다.
    private StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
    private FakeUserRepository fakeUserRepository = new FakeUserRepository();
    private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();
    @BeforeEach
    void setup(){
        userRegister = new UserRegister(stubWeakPasswordChecker, fakeUserRepository, spyEmailNotifier);
    }

    @Test
    public void 암호가_약하면_가입실패(){
        stubWeakPasswordChecker.setWeak(true);

        Assertions.assertThrows(WeakPasswordException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userRegister.register("id", "passwordtest", "email");
            }
        });
    }

    @Test
    public void 이미같은ID가_존재하는_상황만들기(){
        stubWeakPasswordChecker.setWeak(false);
        fakeUserRepository.save(new User("id", "pwq", "email@email.com"));

        Assertions.assertThrows(DuplicateException.class
                , () -> userRegister.register("id", "pwq", "email@email.com"));
    }

    @Test
    public void 같은아이디가_없으면_가입성공함(){
        String id = "id";
        String pwd = "pwd";
        String email = "email@email.com";
        userRegister.register(id, pwd, email);
        User findUser = fakeUserRepository.findById(id);

        Assertions.assertEquals(id, findUser.getId());
        Assertions.assertEquals(pwd, findUser.getPwd());
        Assertions.assertEquals(email, findUser.getEmail());

    }

    @Test
    public void 가입하면_메일전송(){
        userRegister.register("id","pwd","email@email.com");

        Assertions.assertEquals(spyEmailNotifier.getEmail(), "email@email.com");
        Assertions.assertEquals(spyEmailNotifier.isCalled(), true);

    }
}
