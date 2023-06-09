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

const historyRoutes = require('./routes/history-routes')
app.use(historyRoutes)

const bookRoutes = require('./routes/book-routes')
app.use(bookRoutes)

const favoriteRoutes = require('./routes/favorite-routes')
app.use(favoriteRoutes)

const libraryRoutes = require('./routes/library-routes')
app.use(libraryRoutes)

const port = process.env.PORT || 8000
app.listen(port, () => console.log(`server telah berjalan di ${port}`))