package dev.market.spring_market.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dev.market.spring_market.common.BaseEntity;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@ToString
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    private String title;

    private int price;

    private String contents;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<ProductImg> productImages;

    @Builder
	public Product(Long productId, Category category, User user, String title, int price, String contents, List<ProductImg> productImages) {
        super(1);
        this.productId = productId;
		this.category = category;
		this.user = user;
		this.title = title;
		this.price = price;
		this.contents = contents;
		this.productImages = productImages;
	}


    public Product(LocalDateTime createdAt, int status, Long productId, Category category, User user, String title, int price, String contents) {
        super(createdAt, status);
        this.productId = productId;
        this.category = category;
        this.user = user;
        this.title = title;
        this.price = price;
        this.contents = contents;
    }
}
