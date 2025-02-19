package br.com.mysamplecode.springboot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "student")
@Entity
public class Student {

	@Id
	private String id;
	private String name;
}
