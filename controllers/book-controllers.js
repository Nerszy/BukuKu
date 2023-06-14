const { db } = require('../model/productModel')

const getAllBooks = (req, res) => {
  const page = parseInt(req.query.p) || 0;
  const perPage = parseInt(req.query.perPage) || 10;

  db.collection('products')
    .aggregate([
      { $unwind: '$books' },
      { $skip: page * perPage },
      { $limit: perPage },
      {
        $group: {
          _id: null,
          books: { $push: '$books' },
        },
      },
      {
        $project: {
          _id: 0,
          books: '$books',
        },
      },
    ])
    .toArray()
    .then((booksData) => {
      res.status(200).json({ books: booksData[0].books });
    })
    .catch(() => {
      res.status(500).json({ error: 'Tidak dapat mengambil buku' });
    });
};

const getBookDetails = (req, res) => {
  const bookId = parseInt(req.params.id);

  db.collection('products')
    .aggregate([
      { $unwind: '$books' },
      {
        $match: {
          'books.books_id': bookId,
        },
      },
      {
        $project: {
          _id: 0,
          book: '$books',
        },
      },
    ])
    .toArray()
    .then((bookData) => {
      if (bookData.length === 0) {
        res.status(404).json({ error: 'Buku tidak ditemukan' });
      } else {
        res.status(200).json(bookData[0].book);
      }
    })
    .catch((error) => {
      res.status(500).json({ error: error.message });
    });
};

const searchBooks = (req, res) => {
  const searchQuery = req.query.name;

  db.collection('products')
    .aggregate([
      { $unwind: '$books' },
      {
        $project: {
          _id: 0,
          books: '$books',
        },
      },
      {
        $match: {
          'books.title': { $regex: searchQuery, $options: 'i' },
        },
      },
      { $limit: 5 }, // Batasi jumlah buku menjadi 5
    ])
    .toArray()
    .then((booksData) => {
      if (booksData.length === 0) {
        res.status(404).json({ error: 'Buku tidak ditemukan' });
      } else {
        res.status(200).json({ books: booksData.map((item) => item.books) });
      }
    })
    .catch(() => {
      res.status(500).json({ error: 'Tidak dapat mencari buku' });
    });
};

const sortByGenre = (req, res) => {
  const tag = req.query.tag;
  const sortOptions = {
    'books.tags1': 1,
    'books.tags2': 1,
    'books.tags3': 1,
  };

  db.collection('products')
    .aggregate([
      { $unwind: '$books' },
      {
        $match: {
          $or: [
            { 'books.tags1': tag },
            { 'books.tags2': tag },
            { 'books.tags3': tag },
          ],
        },
      },
      {
        $project: {
          _id: 0,
          books: '$books',
        },
      },
      { $sort: sortOptions },
      { $limit: 10 },
    ])
    .toArray()
    .then((booksData) => {
      if (booksData.length === 0) {
        throw new Error('Daftar buku tidak ditemukan');
      }
      res.status(200).json({ books: booksData.map((book) => book.books) });
    })
    .catch((error) => {
      res.status(500).json({ error: error.message });
    });
};

const sortByAlphabet = (req, res) => {
  db.collection('products')
    .aggregate([
      { $unwind: '$books' },
      {
        $project: {
          _id: 0,
          books: '$books',
        },
      },
      { $sort: { 'books.title': 1 } }, // Urutkan berdasarkan judul secara alfabet (ascending)
      { $limit: 10 }, // Batasi jumlah buku menjadi 10
    ])
    .toArray()
    .then((booksData) => {
      res.status(200).json({ books: booksData });
    })
    .catch(() => {
      res.status(500).json({
        error: 'Tidak dapat mengurutkan buku berdasarkan abjad',
      });
    });
};

module.exports = {
  getAllBooks,
  getBookDetails,
  searchBooks,
  sortByGenre,
  sortByAlphabet,
};
