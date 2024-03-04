package ru.alishov.springcourse.Project2Boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishov.springcourse.Project2Boot.models.Book;
import ru.alishov.springcourse.Project2Boot.models.Person;
import ru.alishov.springcourse.Project2Boot.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksServices
{
    private final BooksRepository booksRepository;

    @Autowired
    public BooksServices(BooksRepository booksRepository)
    { this.booksRepository = booksRepository; }

    public List<Book> findAll(boolean sortByYear)
    {
        if (sortByYear)
            return  booksRepository.findAll(Sort.by("yearOfIssue"));
        else
            return  booksRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear)
    {
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findById(int id)
    {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> searchByTitle(String query)
    { return booksRepository.findByBookNameStartingWith(query); }

    public Person getBookOwner(int id)
    { return booksRepository.findById(id).map(Book::getOwner).orElse(null); }

    @Transactional
    public void save(Book book)
    { booksRepository.save(book); }

    @Transactional
    public void update(int id, Book updatedBook)
    {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteById(int id)
    { booksRepository.deleteById(id); }

    @Transactional
    public void release(int id)
    { booksRepository.findById(id).ifPresent( book -> { book.setOwner(null); }); }

    @Transactional
    public void assign(int id, Person selectedPerson)
    { booksRepository.findById(id).ifPresent( book -> { book.setOwner(selectedPerson); }); }
}
