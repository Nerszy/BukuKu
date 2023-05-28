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
            message : 'Data Berhasil Dimuat',
            data : data
        }
    }
}