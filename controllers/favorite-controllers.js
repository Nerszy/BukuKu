const FavoriteBook = require("../model/favorite-model");

const getFavoriteBooks = async (req, res) => {
  try {
    const favoriteBooks = await FavoriteBook.find({});
    return res.status(200).json(favoriteBooks);
  } catch (error) {
    return res.status(500).json({ error: 'Internal server error' });
  }
};

const addFavoriteBook = async (req, res) => {
  const { title, author } = req.body;

  if (!title || !author) {
    return res.status(400).json({ error: 'Title and author are required.' });
  }

  const newBook = new FavoriteBook({ title, author });

  try {
    await newBook.save();
    return res.status(201).json({ message: 'Book added to favorites successfully.' });
  } catch (error) {
    return res.status(500).json({ error: 'Internal server error' });
  }
};

const removeFavoriteBook = async (req, res) => {
  const { title, author } = req.body;

  if (!title || !author) {
    return res.status(400).json({ error: 'Title and author are required.' });
  }

  try {
    const deletedBook = await FavoriteBook.findOneAndDelete({ title, author });
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