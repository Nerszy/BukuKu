const History = require('../model/history-model');

// Mendapatkan semua history baca buku beserta data buku yang terkait
exports.getAllHistories = async (req, res) => {
  try {
    const { username } = req.body; // Mendapatkan nilai username dari inputan

    // Melakukan query berdasarkan username
    const histories = await History.find({ username })
      .sort({ createdAt: -1 })
      .populate('books_id'); 

    const responseData = {
      History: histories
    }

    res.json(responseData);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};


// Membuat atau memperbarui history
exports.updateHistory = async (req, res) => {
  const { username, books_id } = req.body; 

  try {
    let history = await History.findOne({ username, books_id }); 

    if (history) {
      // Jika history sudah ada, geser ke posisi teratas dengan mengubah createdAt
      history.createdAt = Date.now();
    } else {
      // Jika history belum ada, buat history baru
      history = new History({ username, books_id }); 
    }

    await history.save();
    res.status(201).json(history);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};
