package com.devteria.identityservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tag")
public class Tag extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tag_id", updatable = false, nullable = false)
    private String tagId;

    @Column(name = "tag_name", nullable = false, unique = true)
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Product> products;
}
