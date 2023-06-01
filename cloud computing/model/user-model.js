const mongoose = require('mongoose')

const userSchema = mongoose.Schema(
    {
        
        email: String,
        namaLengkap : String,
        username: String,
        password: String
    }

)

module.exports = mongoose.model('users', userSchema)