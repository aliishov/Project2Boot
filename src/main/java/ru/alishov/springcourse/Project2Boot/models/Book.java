package ru.alishov.springcourse.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 200, message = "Name should be between 2 and 200 characters")
    @Column(name = "book_name")
    private String bookName;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 200, message = "Name should be between 2 and 200 characters")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+")
    @Column(name = "author_name")
    private String authorName;

    @Column(name = "year_of_issue")
    @Min(value = 0, message = "Year od issue should be greater than 0")
    private int yearOfIssue;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {}

    public Book(String bookName, String authorName, int yearOfIssue)
    {
        this.bookName = bookName;
        this.authorName = authorName;
        this.yearOfIssue = yearOfIssue;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public int getYearOfIssue() { return yearOfIssue; }
    public void setYearOfIssue(int yearOfIssue) { this.yearOfIssue = yearOfIssue; }

    public Person getOwner() { return owner; }
    public void setOwner(Person owner) { this.owner = owner; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(bookName, book.bookName) && Objects.equals(yearOfIssue, book.yearOfIssue);
    }

    @Override
    public int hashCode()
    { return Objects.hash(id, bookName, yearOfIssue); }

    @Override
    public String toString()
    {
        return "Book {" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", yearOfIssue=" + yearOfIssue +
                '}';
    }
}
