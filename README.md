### 如果项目中你使用的是druid，该jar包能帮助你打印你的SQL语句。

### 使用方式：

```
<dependency>
      <groupId>com.kuyun</groupId>
      <artifactId>sqlLog</artifactId>
      <version>1.0</version>
</dependency>
```

### logback配置(如果不配置，则默认会输出到STDOUT)：

```
<appender name="sqlInfo" class="ch.qos.logback.core.rolling.RollingFileAppender">
      <Encoding>UTF-8</Encoding>
      <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${LOG_HOME}/sqlInfo.%d{yyyy-MM-dd}.log</FileNamePattern>
      </rollingPolicy>
      <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS}|%msg%n</pattern>
      </layout>
</appender>

<loggername="com.kuyun.sqlLog" additivity="false" level="DEBUG">
      <appender-ref ref="sqlInfo" />
</logger>
```

### 如果日志级别是DEBUG会输出所有的curd操作，INFO则不会输出select操作
