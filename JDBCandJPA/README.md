# Useful commands and tips

command to make a migration
```commandline
 mvn clean flyway:migrate
``` 

JDBC - low level.
To work with JDBC RowMapper must be implemented

For JPA with EntityManager:
for the insert query , we wrap the query execution with transactionTemplate.execute() method. Because for any update/delete query we need to use transaction.

We can write native sql query in JpaRepository with “@Query” annotation. (not a raw sql)
```java
@Repository
public interface StoryRepository extends JpaRepository<Story,Long>, StoryCustomRepository {

    @Query("select s from Story s where s.title like %:text%")
    List<Story> searchStoryByTitle(String text);

}
```