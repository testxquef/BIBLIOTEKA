create table author (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), nationality varchar(255), primary key (id)) engine=InnoDB;
create table author_book_id (author_id bigint not null, book_id bigint) engine=InnoDB;
create table author_books (author_id bigint not null, book_id bigint not null, primary key (author_id, book_id)) engine=InnoDB;
create table book (id bigint not null auto_increment, category varchar(255), description longtext, format varchar(255), language varchar(255), pub_year integer not null, publisher varchar(255), title varchar(255), librarian_id bigint, primary key (id)) engine=InnoDB;
create table book_author_id (book_id bigint not null, author_id bigint) engine=InnoDB;
create table librarian (id bigint not null auto_increment, adress longtext, first_name varchar(255), last_name varchar(255), librarian_number integer not null, primary key (id)) engine=InnoDB;
create table librarian_loan_book_id (librarian_id bigint not null, loan_book_id bigint) engine=InnoDB;
alter table author_book_id add constraint FKhd1yxua0y9fu0ojl69gimq8fp foreign key (author_id) references author (id);
alter table author_books add constraint FKgg8l7xyje2rjham3sgxfk2dxm foreign key (book_id) references book (id);
alter table author_books add constraint FKfvabqdr9njwv4khjqkf1pbmma foreign key (author_id) references author (id);
alter table book add constraint FKibmb8mxtjycn35tmjxuck2o2d foreign key (librarian_id) references librarian (id);
alter table book_author_id add constraint FKqvffimh7pniuu2mm8j2rffiwi foreign key (book_id) references book (id);
alter table librarian_loan_book_id add constraint FKnj5gdlm4pe1w37k8s2icy6i6w foreign key (librarian_id) references librarian (id);create table author (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), nationality varchar(255), primary key (id)) engine=InnoDB
