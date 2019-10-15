const mongoose = require('mongoose');

mongoose.connect('mongodb://localhost:27017/test', { useNewUrlParser: true, useUnifiedTopology: true });
// mongoose.set('debug', true);

const PageSchema = {
    id: { type: String, unique: true },
    url: String,
    firstSeen: { type: Date, default: Date.now },
    lastSeen: { type: Date, default: Date.now },
    raw: mongoose.Schema.Types.Mixed
};
const Page = mongoose.model('Page', PageSchema);

module.exports = Page;
