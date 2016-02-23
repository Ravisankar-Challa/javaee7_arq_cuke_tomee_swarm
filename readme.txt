CREATE SEQUENCE MEM_SEQ_GEN START WITH 1 INCREMENT BY 1
CREATE TABLE MEMBER_TABLE (id NUMBER NOT NULL, MEMBER_AGE NUMBER, MEMBER_NAME VARCHAR(32) NOT NULL, PRIMARY KEY (id))
DROP SEQUENCE MEM_SEQ_GEN
DROP TABLE MEMBER_TABLE

java  -DadditionalSystemProperties="-Dorg.tomitribe.sabot.environment=uat" -jar test-exec.jar

mvn clean package tomee:run -Denvironment=qa -Djdbc.driver=oracle.jdbc.OracleDriver -Djdbc.url=jdbc:oacle:thin:@localhost:1521:XE -Djdbc.username=sa -Djdbc.password=sa -Ptomee