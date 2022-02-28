package ECutb.Omar.ali.thymeleafwithdatabase.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    @Column(unique = true)
    private String role;

    public AppRole(String role) {
        this.role = role;
    }

    public AppRole() {
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppRole appRole = (AppRole) o;
        return roleId == appRole.roleId &&
                Objects.equals(role, appRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, role);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppRole{");
        sb.append("roleId=").append(roleId);
        sb.append(", role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
