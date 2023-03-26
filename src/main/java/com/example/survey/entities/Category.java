package com.example.survey.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SVY_CATEGORY")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is mandatory")
    @Size(min = 2)
    private String name;

    @NotBlank(message = "Code is mandatory")
    @Size(min = 2)
    private String code;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10)
    private String description;

}
