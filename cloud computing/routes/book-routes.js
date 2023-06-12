<<<<<<< HEAD:routes/book-routes.js
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

=======
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

>>>>>>> ee76ac28e03ee867a8f2ee0946ff3721a4ab86e4:cloud computing/routes/book-routes.js
module.exports = routes;