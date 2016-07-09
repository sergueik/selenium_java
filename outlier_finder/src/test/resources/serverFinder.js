var git_hashes_str = arguments[0];
var table_selector = 'html body div table.sortable';
var row_selector = 'tbody tr';
var hash_column_selector = 'td:nth-child(3)';
var master_server_column_selector = 'td:nth-child(1)';
var module_column_selector = 'td:nth-child(2)';
var result = {};
var col_num = 0;
var git_hashes = {};
var git_hashes_keys = git_hashes_str.split(',');
for (var key in git_hashes_keys) {
    git_hashes[git_hashes_keys[key]] = 1;
}
var tables = document.querySelectorAll(table_selector);


for (table_cnt = 0; table_cnt != tables.length; table_cnt++) {
    var table = tables[table_cnt];
    if (table instanceof Element) {
        var rows = table.querySelectorAll(row_selector);
        // skip first row
        for (row_cnt = 1; row_cnt != rows.length; row_cnt++) {
            var row = rows[row_cnt];
            if (row instanceof Element) {
                var hash_cols = row.querySelectorAll(hash_column_selector);
                if (hash_cols.length > 0) {
                    var hash_data = hash_cols[0].innerHTML;
                    hash_data = hash_data.replace(/\s+/g, '');
                    if (git_hashes[hash_data]) {
                        var master_server_cols = row.querySelectorAll(master_server_column_selector);
                        if (master_server_cols.length > 0) {
                            var master_server_data = master_server_cols[0].innerHTML;
                            master_server_data = master_server_data.replace(/\\s+/g, '');
                            if (!result[master_server_data]) {
                                result[master_server_data] = hash_data;
                            }
                        }
                    }
                }
            }
        }
    }
}
return JSON.stringify(result);;