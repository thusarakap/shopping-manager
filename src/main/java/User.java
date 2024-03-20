public class User {
    private String username;
    private String password;
    private boolean hasPurchased;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.hasPurchased = false; // Assuming the initial state is false
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isUserRegistered(String username) {
        boolean r = false;
        return r;
    }

    public boolean hasPurchased() {
        return hasPurchased;
    }

    public void setHasPurchased(boolean hasPurchased) {
        this.hasPurchased = hasPurchased;
    }
}
