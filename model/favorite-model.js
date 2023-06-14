const mongoose = require('mongoose');

// Definisikan skema untuk buku favorit
const favoriteBookSchema = new mongoose.Schema({
  title: {
    type: String,
    required: true
  },
  author: {
    type: String,
    required: true
  }
});

// Buat model berdasarkan skema buku favorit
const FavoriteBookModel = mongoose.model('FavoriteBook', favoriteBookSchema);

module.exports = FavoriteBookModel;
