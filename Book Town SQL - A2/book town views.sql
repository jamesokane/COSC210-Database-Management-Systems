
-- COSC210 Practical Assignment 1 Template

-- Please complete the assignment questions using the view templates
-- provided below.

-- *******************************************************************
--                           IMPORTANT
-- *******************************************************************

-- Make sure that you do not alter the names of the views or their
-- attribute values. If you do your assignment will not work in the
-- auto-marking software and you may lose marks!

-- *******************************************************************


CREATE VIEW old_books(first_name,last_name,book_title, edition)
AS SELECT first_name, last_name, title, publication
FROM authors, books, editions
WHERE authors.author_id = books.author_id AND
      books.book_id=editions.book_id AND
      editions.publication < '1990-01-01';


CREATE VIEW programming_or_perl(book_title)
AS SELECT title
FROM books
WHERE title LIKE '%Programming%' OR
      title LIKE '%Perl%';


CREATE VIEW retail_price_hike(ISBN, retail_price, increased_price)
AS SELECT isbn, retail, round(1.25 * retail,2)
FROM stock;


CREATE VIEW book_summary(author_first_name, author_last_name, book_title, subject)
AS SELECT first_name, last_name, title , subject
FROM authors, books, subjects
WHERE authors.author_id = books.author_id AND
      books.subject_id = subjects.subject_id;


CREATE VIEW value_summary(total_stock_cost, total_retail_cost)
AS SELECT cost*stock, retail*stock
FROM stock;


CREATE VIEW profits_by_isbn(book_title, edition_isbn, total_profit)
AS SELECT books.title, shipments.isbn, SUM(stock.retail - stock.cost)
FROM books, editions, shipments, stock
WHERE books.book_id = editions.book_id AND
      editions.isbn = shipments.isbn AND
      shipments.isbn = stock.isbn
GROUP BY books.title, shipments.isbn;



CREATE VIEW sole_python_author(author_first_name, author_last_name)
AS SELECT DISTINCT a1.first_name, a1.last_name
FROM authors AS a1, books AS b1
WHERE a1.author_id = b1.author_id AND
      b1.title LIKE '%Python%' AND
      (SELECT COUNT(DISTINCT a2.author_id)
      FROM authors AS a2, books AS b2
      WHERE a2.author_id = b2.author_id AND
            b2.title LIKE '%Python%') = 1;


CREATE VIEW no_cat_customers(customer_first_name, customer_last_name)
AS SELECT c1.first_name, c1.last_name
FROM customers AS c1
WHERE NOT EXISTS ( SELECT *
                   FROM customers AS c2, shipments, editions, books
                   WHERE c1.c_id = c2.c_id AND
                         c2.c_id=shipments.c_id AND
                         shipments.isbn=editions.isbn AND
                         editions.book_id=books.book_id AND
                         books.title='The Cat in the Hat');
