package com.es.core.entity.jewelry.stock;


import com.es.core.entity.jewelry.Jewelry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jewelryId")
    private Long jewelryId;

    @OneToOne
    @JoinColumn(name = "jewelryId", referencedColumnName = "id", insertable = false, updatable = false)
    private Jewelry jewelry;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "reserved", nullable = false)
    private Integer reserved;
}
