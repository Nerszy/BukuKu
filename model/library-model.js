const mongoose = require('mongoose');

const librarySchema = mongoose.Schema(
  {
    username: String,
    books_id: String,
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model('Library', librarySchema);
