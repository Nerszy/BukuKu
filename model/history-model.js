const mongoose = require('mongoose')

const historySchema = mongoose.Schema(
    {
        username: String,
        books_id: String
    },
    {
        timestamps: true
    }

)

module.exports = mongoose.model('histories', historySchema)