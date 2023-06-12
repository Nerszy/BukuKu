const historyModel = require('../model/history-model')
const response = require('../config/response')

exports.addToHistory = (username, books_id) => {
  return new Promise((resolve, reject) => {
    historyModel
      .findOne({ username: username, books_id: books_id })
      .then((existingHistory) => {
        if (existingHistory) {
          reject(response.ErrorMessage('Book already exists in history'));
        } else {
          const history = new historyModel({
            username: username,
            books_id: books_id
          });

          history
            .save()
            .then(() => resolve(response.HistoryMessage('History has been added')))
            .catch(() => reject(response.ErrorMessage('Failed to add book to history')))
        }
      })
      .catch(() => reject(response.Error));
  });
}
exports.getHistory = (username) => {
    return new Promise((resolve, reject) => {
        historyModel.find({username}).populate('books_id').exec()
        .then(savedHistory => {
          resolve(savedHistory);
        })
        .catch(error => {
          reject(error);
        })
    })
  }