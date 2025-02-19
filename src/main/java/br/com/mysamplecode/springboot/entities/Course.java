package br.com.mysamplecode.springboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table(name = "course")
@Entity
public class Course {
	@Id
	private String id;
	private String name;

	@Column(name = "init_date")
	private LocalDate initDate;

	@Column(name = "end_date")
	private LocalDate endDate;
	private String description;

}
