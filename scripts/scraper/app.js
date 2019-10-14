const _ = require('lodash');
const fs = require('fs');
const moment = require('moment');
const mongoose = require('mongoose');
const parseString = require('xml2js').parseString;


mongoose.connect('mongodb://localhost:27017/test', { useNewUrlParser: true, useUnifiedTopology: true });
const Page = mongoose.model('Page', { id: { type: String, unique: true }, url: String, firstSeen: Date, raw: mongoose.Schema.Types.Mixed });

const xml = fs.readFileSync('./sitemap.xml')

const USEFUL_URLS = /\/Movie\/([^/]+)$/;

const insertPages = async function (pages) {
    new Promise((resolve) => {
        Page.insertMany(pages, { ordered: false }, (error, docs) => {
            resolve();
        });
    });
}

parseString(xml, function (err, result) {
    const commits = _.chain(result.urlset.url)
        .filter(e => e.loc)
        .forEach(e => _.forEach(e, (n, key) => e[key] = n[0]))
        .filter(page => page.loc.includes('/Movie/'))
        .filter(page => {
            const matches = page.loc.match(USEFUL_URLS);
            if (matches) {
                page.id = matches[1];
                return true;
            }
        })
        .map(page => new Page({ id: page.id, url: page.loc, firstSeen: moment().subtract(1, 'day').hour(21).toDate(), raw: page }))
        .tap(console.log)
        .chunk(20)
        .map(insertPages)
        .value()

    console.log(`We have ${commits.length} promises`);

    Promise.all(commits)
        .then(() => mongoose.disconnect(() => console.log("Closed")))
        .catch((reason) => console.log(reason));
});
