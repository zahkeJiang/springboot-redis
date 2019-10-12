package top.thone.pojo;

import lombok.Data;
import top.thone.pojo.enums.Gender;

import javax.persistence.*;

/**
 * @Author thone
 * @Description //TODO
 * @Date 11:28 AM-2019/9/4
 **/
@Data
@Entity
@Table
public class User {
    @Id
    @Basic(optional = false)
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Basic(optional = false)
    @Column
    public String account;

    @Basic(optional = false)
    @Column
    public String password;

    @Basic(optional = false)
    @Column(length = 32)
    public String nickname;

    @Basic(optional = false)
    @Column(columnDefinition = "enum('UNKNOWN', 'MAN', 'WOMAN') DEFAULT 'UNKNOWN'")
    public Gender gender = Gender.UNKNOWN;

    @Basic(optional = false)
    @Column
    public String introduce = "";
}
