// controllers/bookController.js

let books = [];
let library = [];

const getBooks = (req, res) => {
  return res.status(200).json(books);
};

const buyBook = (req, res) => {
  const bookId = req.params.id;
  const book = books.find((book) => book.id === bookId);

  if (!book) {
    return res.status(404).json({ error: 'Book not found.' });
  }

  library.push(book);

  return res.status(201).json({ message: 'Book added to library successfully.' });
};

const getLibrary = (req, res) => {
  return res.status(200).json(library);
};

const yourLibrary = (req, res) => {
    const limit = 4;
    const limitedLibrary = library.slice(0, limit);
    return res.status(200).json(limitedLibrary);
  };

module.exports = {
  getBooks,
  buyBook,
  getLibrary,
  yourLibrary,
};
