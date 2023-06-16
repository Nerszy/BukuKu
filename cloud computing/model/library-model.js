const mongoose = require('mongoose')
const { Schema } = mongoose;

const librarySchema = new Schema({
  user: {
    type: String,
    required: true,
  },
  book: {
    type: Schema.Types.ObjectId,
    ref: 'Product', // Pastikan referensi ini sesuai dengan nama model yang benar
    required: true,
  },
  createdAt: {
    type: Date,
    default: Date.now,
  },
});

const Library = mongoose.model('Library', librarySchema);

module.exports = Library;