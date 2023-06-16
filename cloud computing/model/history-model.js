const mongoose = require('mongoose')

const historySchema = mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    books_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Product',
        required: true
    },
    createdAt: {
        type: Date,
        default: Date.now,
    },
})

module.exports = mongoose.model('histories', historySchema);
