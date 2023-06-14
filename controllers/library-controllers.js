const Library = require('../model/library-model');
const response = require('../config/response');

exports.addBookToLibrary = (username, books_id) => {
  return new Promise((resolve, reject) => {
    Library.findOne({ username: username, books_id: books_id })
      .then((existingBook) => {
        if (existingBook) {
          reject(response.ErrorMessage('Book already exists in library'));
        } else {
          const library = new Library({
            username: username,
            books_id: books_id,
          });

          library
            .save()
            .then(() => resolve(response.LibraryMessage('Book has been added to library')))
            .catch(() => reject(response.ErrorMessage('Failed to add book to library')));
        }
      })
      .catch(() => reject(response.Error));
  });
};

exports.getUserLibrary = (username) => {
  return new Promise((resolve, reject) => {
    Library.find({ username })
      .populate('books_id')
      .exec()
      .then(savedLibrary => {
        resolve(savedLibrary);
      })
      .catch(error => {
        reject(error);
      });
  });
};