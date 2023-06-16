const mongoose = require('mongoose');

// Define the schema for favorite books
const favoriteBookSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true
  },
  book: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Product',
    required: true
  }
});

// Create the model based on the favorite book schema
const FavoriteBook = mongoose.model('FavoriteBook', favoriteBookSchema);

module.exports = FavoriteBook;