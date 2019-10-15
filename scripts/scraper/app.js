const _ = require('lodash');
const fs = require('fs');
const mongoose = require('mongoose');
const parseString = require('xml2js').parseString;
const request = require('sync-request');
const Page = require('./Page')

const xml = fs.readFileSync('./sitemap.xml')
// const xml = request('GET', 'https://www.cineplex.com/sitemaphandlerforgooglecustomsearch.ashx/sitemap.xml').getBody();

const USEFUL_URLS = /\/Movie\/([^/]+)$/;

parseString(xml, function (err, result) {
    let updated = 0, inserted = 0;
    let commits = [];

    const queries = _.chain(result.urlset.url)
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
        .tap(pages => console.log(`Found ${pages.length} pages.`))
        .map(page => Page.findOne({ id: page.id }, (err, doc) => {
            if (err) {
                console.log(err);
            } else if (!doc) {
                inserted++;
                commits.push(new Page(page).save());
            } else {
                updated++;
                doc.lastSeen = new Date();
                commits.push(doc.save());
            }
        }))
        .value();

    Promise.all(queries).then(() => {
        Promise.all(commits)
            .then(() => {
                console.log(`We have ${queries.length} records.  Updated ${updated}; inserted ${inserted}.`);
                () => mongoose.disconnect(() => console.log("Closed"));
            }).catch((reason) => console.log(reason));
    });

});

setTimeout(() => {
    process.exit(25);
}, 10000);
