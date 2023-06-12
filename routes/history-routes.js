const historyControllers = require ('../controllers/history-controllers')
const routes = require('express').Router()

    routes.post('/add', (req, res) => {
        const { username, books_id } = req.body

        historyControllers.addToHistory(username, books_id)
        .then(result => res.json(result))
        .catch(err => res.json(err))
    })

    routes.get('/:username', (req, res) => {
        const { username } = req.params

        historyControllers.getHistory(username)
            .then(result => res.json(result))
            .catch(err => res.json(err))
    })

module.exports = routes