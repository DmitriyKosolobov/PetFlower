FROM liquibase/liquibase:4.25
COPY ./migrations /liquibase/changelog
