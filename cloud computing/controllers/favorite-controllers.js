const FavoriteBook = require("../model/favorite-model");

const getFavoriteBooks = async (req, res) => {
  try {
    const { username } = req.body;

    const favoriteBooks = await FavoriteBook.find({ username }).populate('book');

    const responseData = {
      Favorite: favoriteBooks
    }

    return res.status(200).json(responseData);
  } catch (error) {
    return res.status(500).json({ error: 'Internal server error' });
  }
};

const addFavoriteBook = async (req, res) => {
  const { username, book } = req.body;

  if (!username || !book) {
    return res.status(400).json({ error: 'Username and book are required.' });
  }

  const newBook = new FavoriteBook({ username, book });

  try {
    await newBook.save();
    return res.status(201).json({ message: 'Book added to favorites successfully.' });
  } catch (error) {
    return res.status(500).json({ error: 'Internal server error' });
  }
};

const removeFavoriteBook = async (req, res) => {
  const { username, book } = req.body;

  if (!username || !book) {
    return res.status(400).json({ error: 'Username and book are required.' });
  }

  try {
    const deletedBook = await FavoriteBook.findOneAndDelete({ username, book });

    if (!deletedBook) {
      return res.status(404).json({ error: 'Book not found in favorites.' });
    }

    return res.status(200).json({ message: 'Book removed from favorites successfully.' });
  } catch (error) {
    return res.status(500).json({ error: 'Internal server error' });
  }
};

module.exports = {
  getFavoriteBooks,
  addFavoriteBook,
  removeFavoriteBook
};
