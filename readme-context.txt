<Resource name="jdbc/freeneoSurvey" auth="Container" type="javax.sql.DataSource"
	maxActive="100" maxIdle="30" maxWait="10000" username="SYSTEM"
	password="freeneo" driverClassName="oracle.jdbc.driver.OracleDriver"
	url="jdbc:oracle:thin:@mangosg.freeneo.com:1521:survey" />

<Resource name="jdbc/freeneoSurveyInner" auth="Container"
	type="javax.sql.DataSource" maxActive="100" maxIdle="30" maxWait="10000"
	username="SYSTEM" password="freeneo" driverClassName="oracle.jdbc.driver.OracleDriver"
	url="jdbc:oracle:thin:@mangosg.freeneo.com:1521:surveyinner" />

<Resource name="jdbc/crm" auth="Container" type="javax.sql.DataSource"
	maxActive="100" maxIdle="30" maxWait="10000" username="SYSTEM"
	password="freeneo" driverClassName="oracle.jdbc.driver.OracleDriver"
	url="jdbc:oracle:thin:@mangosg.freeneo.com:1521:crm" />

<Resource name="jdbc/mms" auth="Container" type="javax.sql.DataSource"
	maxActive="100" maxIdle="30" maxWait="10000" username="SYSTEM"
	password="freeneo" driverClassName="oracle.jdbc.driver.OracleDriver"
	url="jdbc:oracle:thin:@mangosg.freeneo.com:1521:smsnew" />