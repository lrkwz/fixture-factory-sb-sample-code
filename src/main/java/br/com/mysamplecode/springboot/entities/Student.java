package br.com.mysamplecode.springboot.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "student")
@Entity
public class Student {

    @Id
    private String id;
    private String name;
}
