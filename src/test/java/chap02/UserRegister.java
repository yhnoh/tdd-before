package chap02;

public class UserRegister {
    private final WeakPasswordChecker weakPasswordChecker;
    private final UserRepository userRepository;
    private final EmailNotifier emailNotifier;
    /**
     * Stub : 구현을 단순한 것으로 대체한다.
     */
    public UserRegister(WeakPasswordChecker weakPasswordChecker, UserRepository userRepository, EmailNotifier emailNotifier) {
        this.weakPasswordChecker = weakPasswordChecker;
        this.userRepository = userRepository;
        this.emailNotifier = emailNotifier;
    }

    public void register(String id, String password, String email) {
        if(weakPasswordChecker.checkPasswordWeak(password)){
            throw new WeakPasswordException();
        }
        User findUser = userRepository.findById(id);
        if(findUser != null){
            throw new DuplicateException();
        }
        User user = new User(id, password, email);
        userRepository.save(user);

        emailNotifier.sendRegisterEmail(email);
    }
}
