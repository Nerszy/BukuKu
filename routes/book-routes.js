const express = require('express');
const routes = express.Router();

const {
  getAllBooks,
  getBookDetails,
  searchBooks,
  sortByGenre,
  sortByAlphabet
} = require('../controllers/book-controllers');

// Routes
routes.get('/book', getAllBooks);
routes.get('/book/details/:id', getBookDetails);
routes.get('/book/search', searchBooks);
routes.get('/book/sortByGenre', sortByGenre);
routes.get('/book/sortByAlphabet', sortByAlphabet);

module.exports = routes;