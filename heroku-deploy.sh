heroku login
heroku buildpacks:clear    
heroku plugins:install java
heroku war:deploy src/java/target/online-examination.war --app online-examination-app