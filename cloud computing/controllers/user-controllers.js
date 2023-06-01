const userModel = require('../model/user-model')
const bcrypt = require('bcrypt')
const response = require('../config/response')


exports.regis = (data) => 
    new Promise((resolve, reject) => {
        userModel.findOne({$or: [{email: data.email}, {username: data.username}]})
            .then(user => {
                if (user){
                    if (user.email === data.email) {
                        resolve(response.ErrorMessage('Email has been used'))
                    } else {
                        resolve(response.ErrorMessage('Username has been used'))
                    }
                }else {
                    bcrypt.hash(data.password, 10, (err, hash) => {
                        if (err) {
                            reject(response.ErrorMessage)
                        }else {
                            data.password = hash
                            userModel.create(data)
                                .then(() => resolve(response.SuccessMessage('Registration Success')))
                                .catch(() => reject(response.ErrorMessage('Registration failed')) )
                        }
                    })
                }
            }) .catch(() => reject(response.Error))
    })

exports.login = (data) => 
    new Promise((resolve, reject) => {
    userModel.findOne({email: data.email})
        .then(user => {
            if(user){
                if(bcrypt.compareSync(data.password, user.password)){
                    resolve(response.Result(user))
                }else {
                    reject(response.ErrorMessage('Wrong Password'))
                }
            }else {
                reject(response.ErrorMessage('Email not found'))
            }
        })
    })