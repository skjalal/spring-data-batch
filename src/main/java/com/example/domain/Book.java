package com.example.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "book")
public class Book {

  @Id
  private long bookId;
  private String title;
  private String authors;
  private String isbn;
}
