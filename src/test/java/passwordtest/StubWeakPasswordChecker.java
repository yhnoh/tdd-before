package passwordtest;

import chap02.WeakPasswordChecker;

/**
 * 스텁 : 구현체를 단순한 것으로 대체한다.
 */
public class StubWeakPasswordChecker implements WeakPasswordChecker {

    private boolean weak;

    @Override
    public void setWeak(boolean weak) {
        this.weak = weak;
    }

    @Override
    public boolean checkPasswordWeak(String pwd) {
        return weak;
    }
}
