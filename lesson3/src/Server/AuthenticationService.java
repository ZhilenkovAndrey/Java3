package Server;


import java.util.*;

public class AuthenticationService {

    CustomerService customerService = new CustomerService();

    Set<User> users = Set.of(
            new User(customerService.findAll().get(0).userName(),
                    customerService.findAll().get(0).login(),
                    customerService.findAll().get(0).password()),

            new User(customerService.findAll().get(1).userName(),
                    customerService.findAll().get(1).login(),
                    customerService.findAll().get(1).password()),

            new User(customerService.findAll().get(2).userName(),
                    customerService.findAll().get(2).login(),
                    customerService.findAll().get(2).password())
    );

    public Optional<String> findUsernameByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .map(User::getUsername);
    }

    class User {
        private String username;
        private String login;
        private String password;

        public User(String username, String login, String password) {
            this.username = username;
            this.login = login;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        @Override

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(username, user.username) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, login, password);
        }
    }
}