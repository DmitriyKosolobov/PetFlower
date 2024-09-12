FROM liquibase/liquibase:4.25
COPY backend/migrations /liquibase/changelog
