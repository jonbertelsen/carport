package app.entities;

public class User
{
    private int userId;
    private String userName;
    private String password;
    private String role;

    public User(int userId, String userName, String password, String role)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole()
    {
        return role;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return getUserId() == user.getUserId() && getUserName().equals(user.getUserName()) && getPassword().equals(user.getPassword()) && getRole().equals(user.getRole());
    }

    @Override
    public int hashCode()
    {
        int result = getUserId();
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getRole().hashCode();
        return result;
    }
}
