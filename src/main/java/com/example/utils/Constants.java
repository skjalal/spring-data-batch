package com.example.utils;

public final class Constants {

  private Constants() {}

  private static final String[] FIELD_NAMES = {
    "bookId",
    "title",
    "authors",
    "average_rating",
    "isbn",
    "isbn13",
    "language_code",
    "num_pages",
    "ratings_count",
    "text_reviews_count",
    "publication_date",
    "publisher"
  };

  public static String[] getFieldNames() {
    return FIELD_NAMES.clone();
  }

  public static final String WRITE_SQL = "INSERT INTO book (book_id,title,authors,isbn) VALUES (:bookId,:title,:authors,:isbn)";
  public static final String BOOK_READER = "bookItemReader";
  public static final String INPUT_FILE = "./data/books.csv";
  public static final String STEP = "step";
  public static final String IMPORT_USER_JOB = "importUserJob";
  public static final int CHUNK_SIZE = 20;
  public static final String READ_SQL = "SELECT book_id, title, authors FROM book";
}
