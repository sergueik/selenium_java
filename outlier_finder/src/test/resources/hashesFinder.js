var table_selector = 'html body div table.sortable';
var row_selector = 'tbody tr';
var column_selector = 'td:nth-child(3)';
col_num = 0;
var tables = document.querySelectorAll(table_selector);
var git_hashes = {};
for (table_cnt = 0; table_cnt != tables.length; table_cnt++) {
    var table = tables[table_cnt];
    if (table instanceof Element) {
        var rows = table.querySelectorAll(row_selector);
        // skip first row
        for (row_cnt = 1; row_cnt != rows.length; row_cnt++) {
            var row = rows[row_cnt];
            if (row instanceof Element) {
                var cols = row.querySelectorAll(column_selector);
                if (cols.length > 0) {
                    data = cols[0].innerHTML;
                    data = data.replace(/\s+/g, '');
                    if (!git_hashes[data]) {
                        git_hashes[data] = 0;
                    }
                    git_hashes[data]++;
                }
            }
        }
    }
}
var sortNumber = function(a, b) {
    // reverse numeric sort
    return b - a;
}
var removeMaxValue = function(datahash) {
    var array_values = [];
    for (var key in datahash) {
        array_values.push(0 + datahash[key]);
    }
    max_value = array_values.sort(sortNumber)[0]
    for (var key in datahash) {
        if (datahash[key] === max_value) {
            delete datahash[key]
        }
        // some modules may have less servers in total
        if (datahash[key] >= max_value *.5 ) {
          delete datahash[key]
        }
    }
    return datahash;
}

git_hashes = removeMaxValue(git_hashes);

var array_keys = [];
for (var key in git_hashes) {
    array_keys.push(key);
}
// var git_hashes_str =
// array_keys.join();
return  JSON.stringify(array_keys);