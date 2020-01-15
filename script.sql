create table ag_file
(
    id_file    bigint auto_increment
        primary key,
    file_name  varchar(100) not null,
    mime_type  varchar(100) not null,
    path       varchar(100) not null,
    score_type varchar(100) not null,
    status     int          not null,
    tx_user    varchar(50)  not null,
    tx_host    varchar(100) not null,
    tx_date    date         not null
);

create table ag_person
(
    id_person  bigint auto_increment
        primary key,
    first_name varchar(100) null,
    last_name  varchar(100) null,
    status     int          not null,
    tx_user    varchar(50)  not null,
    tx_host    varchar(100) not null,
    tx_date    date         not null
);

create table ag_user
(
    id_user     bigint auto_increment
        primary key,
    id_person   bigint       not null,
    bot_user_id varchar(100) not null,
    status      int          not null,
    tx_user     varchar(50)  not null,
    tx_host     varchar(100) not null,
    tx_date     date         not null,
    constraint ag_user_ag_person
        foreign key (id_person) references ag_person (id_person)
);

create table ag_contact
(
    id_contact bigint auto_increment
        primary key,
    id_person  bigint       not null,
    id_user    bigint       not null,
    correo     varchar(100) not null,
    fecha_nac  date         not null,
    status     int          not null,
    tx_user    varchar(50)  not null,
    tx_host    varchar(100) not null,
    tx_date    date         not null,
    constraint ag_contact_ag_person
        foreign key (id_person) references ag_person (id_person),
    constraint ag_contact_ag_user
        foreign key (id_user) references ag_user (id_user)
);

create table ag_contact_file
(
    id_contact_file bigint auto_increment
        primary key,
    id_contact      bigint       not null,
    id_file         bigint       not null,
    status          int          not null,
    tx_user         varchar(50)  not null,
    tx_host         varchar(100) not null,
    tx_date         date         not null,
    constraint ag_contact_file_ag_contact
        foreign key (id_contact) references ag_contact (id_contact),
    constraint ag_contact_file_ag_file
        foreign key (id_file) references ag_file (id_file)
);

create table ag_phone
(
    id_phone   bigint auto_increment
        primary key,
    id_contact bigint       not null,
    phone      int          not null,
    status     int          not null,
    tx_user    varchar(50)  not null,
    tx_host    varchar(100) not null,
    tx_date    date         not null,
    constraint ag_phone_ag_contact
        foreign key (id_contact) references ag_contact (id_contact)
);


