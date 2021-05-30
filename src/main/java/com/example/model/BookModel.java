package com.example.model;

import lombok.Data;

@Data
public class BookModel {

  private String bookId;
  private String title;
  private String authors;
  private String averageRating;
  private String isbn;
  private String isbn13;
  private String languageCode;
  private String numPages;
  private String ratingsCount;
  private String textReviewsCount;
  private String publicationDate;
  private String publisher;
}
