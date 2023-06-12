<<<<<<< HEAD:routes/user-routes
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
=======
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
>>>>>>> ee76ac28e03ee867a8f2ee0946ff3721a4ab86e4:cloud computing/routes/user-routes.js
module.exports = routes