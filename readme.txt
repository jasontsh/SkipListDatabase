This is a java implementation of Skip List Database.

The commands are inside Main.java.

Queries:
To initiate a list:
init <classname>
Example: init user

To insert a databaseobject:
INSERT INTO <classname> <column names separated with comma> VALUES <values corresponding to the columns>
Example: INSERT INTO USER (username,password) VALUES (u,p)

To select a databaseobject:
Select <Key> from <classname>
Example: select 3 from user
Return: "" if cannot find the object, otherwise the json of the object.