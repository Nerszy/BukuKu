const Library = require('../model/library-model');

module.exports = {
  create: async (req, res) => {
    const username = req.body.username;
    const books_id = req.body.books_id;

    const book = new Library({
      user: username,
      book: books_id
    });

    try {
      const libraryData = await book.save();
      return res.json(libraryData);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  },

  getLibrary: async (req, res) => {
    const username = req.body.username;

    try {
      const libraryData = await Library.find({ user: username }).populate('book');
      const responseData = {
        library: libraryData
      };
      res.json(responseData);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Internal Server Error' });
    }
  }
};
