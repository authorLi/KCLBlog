package com.mycclee.blog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author mycclee
 * @createTime 2019/7/11 10:11
 */
@Entity
@Table(name = "KCL_ROLES")
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "available")
    private Boolean available = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "ROLES_USERS",joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"))
    private List<User> userList;

    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinTable(name = "ROLES_PERMISSIONS",joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "id"))
    private List<Permission> permissionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
