CREATE TABLE Book (
	id INT NOT null auto_increment,
	userId numeric,
    title varchar(256),
    body varchar(256),
    PRIMARY KEY (id)
);