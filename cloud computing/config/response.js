<<<<<<< HEAD:config/response.js
module.exports = {

    Error:{
        err: true,
        message : 'Terjadi kesalahan'
    },
    ErrorMessage: (message) => {
        return {
            err: true,
            message : message
        }
    },
    Success:{
        err: false,
        message : 'Permintaan Berhasil Dijalankan'
    },
    SuccessMessage: (message) => {
        return {
            err: false,
            message : message
        }
    },
    Result: (data) => {
        return {
            err : false,
            message : 'Login Succesfully',
            data : data
        }
    },
    HistoryMessage : (message) => {
        return {
            err: false,
            message : message
        }
    }
=======
module.exports = {

    Error:{
        err: true,
        message : 'Terjadi kesalahan'
    },
    ErrorMessage: (message) => {
        return {
            err: true,
            message : message
        }
    },
    Success:{
        err: false,
        message : 'Permintaan Berhasil Dijalankan'
    },
    SuccessMessage: (message) => {
        return {
            err: false,
            message : message
        }
    },
    Result: (data) => {
        return {
            err : false,
            message : 'Login Succesfully',
            data : data
        }
    },
    HistoryMessage : (message) => {
        return {
            err: false,
            message : message
        }
    }
>>>>>>> ee76ac28e03ee867a8f2ee0946ff3721a4ab86e4:cloud computing/config/response.js
}