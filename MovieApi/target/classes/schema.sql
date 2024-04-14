drop table if exists movies;


create table movie_cast (
       movie_movie_id integer not null,
       movie_cast varchar(255)
)

create table movies (
        movie_id integer not null auto_increment,
        release_year integer not null,
        title varchar(200) not null,
        director varchar(255) not null,
        poster_path varchar(255) not null,
        studio varchar(255) not null,
        primary key (movie_id)
    )

alter table movie_cast
    add constraint FKfl2bk45gsaqifotiu420y7gtm
        foreign key (movie_movie_id)
            references movies (movie_id)

