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
    userModel.findOne({username: data.username})
        .then(user => {
            if(user){
                if(bcrypt.compareSync(data.password, user.password)){
                    resolve(response.Result(user))
                }else {
                    reject(response.ErrorMessage('Wrong Password'))
                }
            }else {
                reject(response.ErrorMessage('Username not found'))
            }
        })
    })

exports.changePassword = (data) =>
    new Promise((resolve, reject) => {
      userModel.findOne({ username: data.username })
        .then(user => {
          if (user) {
            bcrypt.compare(data.currentPassword, user.password, (err, result) => {
              if (err) {
                reject(response.ErrorMessage('Error comparing passwords'));
              } else {
                if (result) {
                  bcrypt.hash(data.newPassword, 10, (err, hash) => {
                    if (err) {
                      reject(response.ErrorMessage('Error hashing password'));
                    } else {
                      user.password = hash;
                      user.save()
                        .then(() => resolve(response.SuccessMessage('Password changed successfully')))
                        .catch(() => reject(response.ErrorMessage('Failed to change password')));
                    }
                  });
                } else {
                  reject(response.ErrorMessage('Current password is incorrect'));
                }
              }
            });
          } else {
            reject(response.ErrorMessage('User not found'));
          }
        })
        .catch(() => reject(response.Error));
    });
  