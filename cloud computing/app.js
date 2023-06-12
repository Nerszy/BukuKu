const express = require("express")
const cors = require("cors")
const db = require("./config/database")
const app = express()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')

mongoose.connect(db.mongoURL, {
    useNewUrlParser : true
})
    .then(() => console.log("Database Connected"))
    .catch(err => {
        console.log(`Failed Connect ${err.message}`)
    })
app.use(cors())
app.use(bodyParser.json({
    extended: true,
    limit: '50mb'
}))
app.use(bodyParser.urlencoded({
    extended: true,
    limit: '50mb'
}))
app.use('/user', require('./routes/user-routes'))
app.use('/history', require('./routes/history-routes'))

const bookRoutes = require('./routes/book-routes')
app.use(bookRoutes)

const port = process.env.PORT || 8000
app.listen(port, () => console.log(`server telah berjalan di ${port}`))