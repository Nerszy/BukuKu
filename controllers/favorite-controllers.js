// Data sementara untuk menyimpan buku favorit
let favoriteBooks = [];

const getFavoriteBooks = (req, res) => {
    return res.status(200).json(favoriteBooks);
  };

const addFavoriteBook = (req, res) => {
  const { title, author } = req.body;

  if (!title || !author) {
    return res.status(400).json({ error: 'Title and author are required.' });
  }

  const newBook = {
    title,
    author
  };

  favoriteBooks.push(newBook);

  return res.status(201).json({ message: 'Book added to favorites successfully.' });
};

const removeFavoriteBook = (req, res) => {
  const { title, author } = req.body;

  if (!title || !author) {
    return res.status(400).json({ error: 'Title and author are required.' });
  }

  const bookToRemove = {
    title,
    author
  };

  const index = favoriteBooks.findIndex(book => book.title === title && book.author === author);

  if (index !== -1) {
    favoriteBooks.splice(index, 1);
    return res.status(200).json({ message: 'Book removed from favorites successfully.' });
  } else {
    return res.status(404).json({ error: 'Book not found in favorites.' });
  }
};

module.exports = {
    getFavoriteBooks,
    addFavoriteBook,
    removeFavoriteBook
};
