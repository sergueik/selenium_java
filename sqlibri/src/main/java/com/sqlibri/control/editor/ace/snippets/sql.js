define("ace/snippets/sql",["require","exports","module"],
function(e,t,n){"use strict";
t.snippetText="snippet tbl\n	CREATE TABLE ${1:table} (\n		${2:columns}\n	);\
\nsnippet col\n	${1:name}	${2:type}	${3:default ''}	${4:not null}\
\nsnippet ccol\n	${1:name}	varchar2(${2:size})	${3:default ''}	${4:not null}\
\nsnippet ncol\n	${1:name}	number	${3:default 0}	${4:not null}\
\nsnippet dcol\n	${1:name}	date	${3:default sysdate}	${4:not null}\
\nsnippet ind\n	CREATE INDEX ${3:$1_$2} ON ${1:table}(${2:column});\
\nsnippet uind\n	CREATE UNIQUE INDEX ${1:name} ON ${2:table}(${3:column});\
\nsnippet tblcom\n	COMMENT ON TABLE ${1:table} IS '${2:comment}';\
\nsnippet colcom\n	COMMENT ON COLUMN ${1:table}.${2:column} IS '${3:comment}';\
\nsnippet addcol\n	ALTER TABLE ${1:table} ADD (${2:column} ${3:type});\
\nsnippet seq\n	CREATE SEQUENCE ${1:name} START WITH ${2:1} INCREMENT BY ${3:1} MINVALUE ${4:1};\
\nsnippet s*\n	SELECT * FROM ${1:table};\
\nsnippet ins\n	INSERT INTO ${1:table} VALUES(${2:values});\
\nsnippet upt\n	UPDATE ${1:table} SET ${2:field} = ${3:value};\
\nsnippet dt\n	DROP TABLE ${1:table} IF EXSISTS;\
\nsnippet dl\n	DELETE FROM ${1:table} WHERE ${2:field}=${3:value};\n",t.scope="sql"
});
