package dev.market.spring_market.entity;

import dev.market.spring_market.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name = "user")
@NoArgsConstructor

public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String userEmail;

    private String password;

    private String nickname;

    private char gender;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Product> products;

    public User(Long userId, String userEmail, String password, String nickname, char gender, List<Product> products) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.products = products;
    }

    public User(String userEmail, String password, String nickname, char gender) {
        super(1);
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
    }

    public User(Long userId, String userEmail, String password, String nickname, char gender, LocalDateTime createdAt) {
        super(createdAt);
        this.userId=userId;
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;

    }

    public User(Long userId, String userEmail, String password, String nickname, char gender, int status, LocalDateTime createdAt) {
        super(createdAt,status);
        this.userId=userId;
        this.userEmail = userEmail;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;

    }
}
