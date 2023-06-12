const userControllers = require("../controllers/user-controllers")
const routes = require("express").Router()

    routes.post('/regis', (req, res) => {
        userControllers.regis(req.body)
            .then((result)=> res.json(result))
            .catch((err) => res.json(err)) 
    })
    routes.post('/login', (req, res) => {
        userControllers.login(req.body)
          .then((result) => {
            delete result.data;
            res.json(result);
          })
          .catch((err) => res.json(err));
      })
    routes.post('/changePassword', (req, res) => {
        userControllers.changePassword(req.body)
            .then((result) => res.json(result))
            .catch((err) => res.json(err))
})
module.exports = routes