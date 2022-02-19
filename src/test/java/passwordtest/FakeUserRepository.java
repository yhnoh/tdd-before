package passwordtest;

import chap02.DuplicateException;
import chap02.User;
import chap02.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * 가짜 : 외부 DB와같은 연동을 위해서 가짜 객체를 만든다.
 */
public class FakeUserRepository implements UserRepository {

    Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(), user);

    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }
}
