package com.es.core.entity.jewelry;

import com.es.core.entity.jewelry.color.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jewelries")
public class Jewelry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "displaySizeInches")
    private BigDecimal displaySizeInches;

    @Column(name = "weightGr")
    private Integer weightGr;

    @Column(name = "type")
    private BigDecimal type;

    @Column(name = "materialId")
    private BigDecimal materialId;

    @Column(name = "stoneId")
    private BigDecimal stoneId;

    @Column(name = "announced")
    @Temporal(TemporalType.TIMESTAMP)
    private Date announced;

    @Column(name = "deviceType")
    private String deviceType;

    @Column(name = "category")
    private String category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "jewelry2color",
            joinColumns = @JoinColumn(name = "jewelryId"),
            inverseJoinColumns = @JoinColumn(name = "colorId"))
    private Set<Color> colors = new HashSet<>();

    @Column(name = "positioning")
    private String positioning;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "description", length = 4096)
    private String description;
}
