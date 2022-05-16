# jamusic-application-server
This is the application server software for Jamusic project.
A fully functional application software that delivers artists and their albums info, manages users, schedules different types of notification (e.g. SMS, FCM, and Email), manages promotion links that appear on the main page of the mobile client, and also manages different kinds of bug reports filed by end-users.<br/><br/>
It uses Java (along with Spark microframework), Postgres for data storage, Hibernate (JPA) for ORM, Casbin for access control, and JWT for user authentication and authorization. It handles over 60 different use cases for both administrators and subscribers of the system.<br/>
You can find Domain-driven desgin principles (e.g. Aggregates, Value Objects, etc.), Domain Events, CQRS, batch processing, domain data mapping, and Domain Services.
