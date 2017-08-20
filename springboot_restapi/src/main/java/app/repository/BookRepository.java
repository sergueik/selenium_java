package app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
