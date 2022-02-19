package passwordtest;

import chap02.EmailNotifier;

/**
 * 스텀과 스파이의 차이를 잘 모르겠다... 아직
 */
public class SpyEmailNotifier implements EmailNotifier {

    private boolean called;
    private String email;

    @Override
    public void sendRegisterEmail(String email) {
        this.called = true;
        this.email = email;
    }

    public boolean isCalled() {
        return called;
    }

    public String getEmail() {
        return email;
    }
}
