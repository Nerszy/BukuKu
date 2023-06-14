const mongoose = require('mongoose')

const productSchema = mongoose.Schema(
    {
        books_id: {
            type: Number,
            required:true,
        },
        title: {
            type: String,
            required: [true, "Please enter a title"]
        },
        url_playbook: {
            type: String,
            required: false,
        },
        url_image: {
            type: String,
            required: false,
        },
        synopsis: {
            type: String,
            required: true,
        },
        idr: {
            type: String,
            required: true,
        },
        isbn: {
            type: Number,
            required: true,
        },
        author: {
            type: String,
            required: true,
        },
        avg_rating: {
            type: String,
            required: true,
        },
        tags1: {
            type: String,
            required: true,
        },
        tags2: {
            type: String,
            required: true,
        },
        tags3: {
            type: String,
            required: true,
        }
    },
    {
        timestamps: true
    }
)

const Product = mongoose.model('Product', productSchema);

module.exports = Product;