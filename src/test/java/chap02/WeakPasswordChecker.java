package chap02;

public interface WeakPasswordChecker {
    void setWeak(boolean b);

    boolean checkPasswordWeak(String pwd);
}
