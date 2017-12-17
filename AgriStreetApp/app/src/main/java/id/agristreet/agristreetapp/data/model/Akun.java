package id.agristreet.agristreetapp.data.model;

/**
 * Created by RyMey on 12/17/17.
 */

public class Akun {
    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Akun{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
